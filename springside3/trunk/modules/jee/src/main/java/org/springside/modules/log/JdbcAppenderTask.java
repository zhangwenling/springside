/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springside.modules.queue.QueueConsumerTask;

/**
 * 将Queue中的log4j event写入数据库的消费者任务.
 * 使用Jdbc批量写入的模式.
 * 
 * @author calvin
 */
public class JdbcAppenderTask extends QueueConsumerTask {

	protected SimpleJdbcTemplate jdbcTemplate;
	protected String sql;
	protected int bufferSize = 10;
	protected List<LoggingEvent> eventBuffer = new ArrayList<LoggingEvent>();

	@Required
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * 带Named Parameter的insert sql.
	 * 
	 * Named Parameter的名称见Log4jUtils中的常量定义.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 批量执行的队列大小, 默认为10.
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	/**
	 * 线程执行函数.
	 */
	public void run() {
		try {
			//循环阻塞获取消息
			while (!Thread.currentThread().isInterrupted()) {
				Object event = queue.take();
				processEvent(event);
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断", e);
		}
		clean();
	}

	/**
	 * 事件处理函数.
	 */
	protected void processEvent(Object eventObject) {
		LoggingEvent event = (LoggingEvent) eventObject;
		eventBuffer.add(event);
		logger.debug("get event, {}", Log4jUtils.convertEventToString(event));

		//已到达BufferSize则执行批量插入操作
		if (eventBuffer.size() >= bufferSize) {
			updateBatch();
		}
	}

	/**
	 * 退出清理函数.
	 */
	protected void clean() {
		if (eventBuffer.size() > 0) {
			updateBatch();
		}
		logger.debug("cleaned task {}", this);
	}

	/**
	 * 批量插入Buffer中的事件的数据库.
	 */
	@SuppressWarnings("unchecked")
	protected void updateBatch() {
		try {
			List<Map<String, Object>> paramMapList = new ArrayList<Map<String, Object>>();
			for (LoggingEvent event : eventBuffer) {
				Map<String, Object> paramMap = parseEvent(event);
				paramMapList.add(paramMap);
			}

			Map[] paramMapArray = paramMapList.toArray(new Map[paramMapList.size()]);
			SqlParameterSource[] paramMapBatch = SqlParameterSourceUtils.createBatch(paramMapArray);

			try {
				jdbcTemplate.batchUpdate(getActualSql(), paramMapBatch);
				if (logger.isDebugEnabled()) {
					for (LoggingEvent event : eventBuffer) {
						logger.debug("saved event, {}", Log4jUtils.convertEventToString(event));
					}
				}
			} catch (DataAccessException e) {
				dataAccessExceptionHandle(e, eventBuffer);
			}

			eventBuffer.clear();
		} catch (Exception e) {
			logger.error("批量提交任务时发生错误.", e);
		}

	}

	/**
	 * 分析Event, 建立Parameter Map, 用于绑定sql中的Named Parameter.
	 */
	protected Map<String, Object> parseEvent(LoggingEvent event) {
		Map<String, Object> paramMap = Log4jUtils.convertEventToMap(event);
		postParseEvent(event, paramMap);
		return paramMap;
	}

	/**
	 * 可被子类重载的数据访问错误处理函数.
	 */
	protected void dataAccessExceptionHandle(RuntimeException e, List<LoggingEvent> eventBatch) {
		for (LoggingEvent event : eventBatch) {
			logger.error("event in batch is not correct, ignore it, " + Log4jUtils.convertEventToString(event), e);
		}
	}

	/**
	 * 可被子类重载的sql提供函数,可对sql语句进行特殊处理，如日志表的表名可带日期后缀 LOG_2009_02_31.
	 */
	protected String getActualSql() {
		return sql;
	}

	/**
	 * 可被子类重载的事件分析函数,可进行进一步的分析工作,如对message字符串进行分解等.
	 */
	protected void postParseEvent(LoggingEvent event, Map<String, Object> paramMap) {
	}
}
