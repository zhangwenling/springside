package org.springside.modules.log;

/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JdbcAppenderTask.java 353 2009-08-22 09:33:28Z calvinxiu
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springside.modules.queue.QueueConsumerTask;

/**
 * 将Queue中的log4j event写入数据库的消费者任务.
 * 即时阻塞的读取Queue中的事件,达到缓存上限后使用Jdbc批量写入模式.
 * 
 * @see QueueConsumerTask
 * 
 * @author calvin
 */
public class JdbcBlockingFetchAppenderTask extends QueueConsumerTask {

	protected SimpleJdbcTemplate jdbcTemplate;
	protected String sql;
	protected int batchSize = 10;

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
	 * 批量读取事件数量, 默认为10.
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * 线程执行函数,阻塞获取消息并调用processMessage()进行处理.
	 */
	public void run() {
		//循环阻塞获取消息直到线程被中断.
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Object message = queue.take();
				processMessage(message);
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断");
		}

		//在线程被中断后,退出线程前的清理函数.
		clean();
	}

	/**
	 * 消息处理函数,将消息放入buffer,当buffer达到batchSize时执行批量消息处理函数.
	 */
	protected void processMessage(Object message) {
		LoggingEvent event = (LoggingEvent) message;
		eventBuffer.add(event);
		logger.debug("get event, {}", Log4jUtils.convertEventToString(event));

		//已到达BufferSize则执行批量插入操作
		if (eventBuffer.size() >= batchSize) {
			processMessageList(eventBuffer);
		}
	}

	/**
	 * 批量消息处理函数,将事件列表批量插入数据库.
	 */
	@SuppressWarnings("unchecked")
	protected void processMessageList(List messageList) {
		List<LoggingEvent> eventList = messageList;
		List<Map<String, Object>> paramMapList = new ArrayList<Map<String, Object>>();

		try {
			//分析事件列表,转换为jdbc参数.
			for (LoggingEvent event : eventList) {
				Map<String, Object> paramMap = parseEvent(event);
				paramMapList.add(paramMap);
			}
			Map[] paramMapArray = paramMapList.toArray(new Map[paramMapList.size()]);
			SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(paramMapArray);

			//执行批量插入,失败时调用失败处理函数.
			try {
				jdbcTemplate.batchUpdate(getActualSql(), batchParams);
				if (logger.isDebugEnabled()) {
					for (LoggingEvent event : eventBuffer) {
						logger.debug("saved event, {}", Log4jUtils.convertEventToString(event));
					}
				}
			} catch (DataAccessException e) {
				handleDataAccessException(e, eventBuffer);
			}

			//清除eventBuffer
			eventBuffer.clear();
		} catch (Exception e) {
			logger.error("批量提交任务时发生错误.", e);
		}
	}

	/**
	 * 退出清理函数,完成buffer中未完成的消息.
	 */
	protected void clean() {
		if (!eventBuffer.isEmpty()) {
			processMessageList(eventBuffer);
		}
		logger.debug("cleaned task {}", this);
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
	protected void handleDataAccessException(DataAccessException e, List<LoggingEvent> errorEventBatch) {
		if (e instanceof DataAccessResourceFailureException) {
			logger.error("database connection error", e);
		} else {
			logger.error("other database error", e);
		}

		for (LoggingEvent event : errorEventBatch) {
			logger.error("event insert to database error, ignore it, " + Log4jUtils.convertEventToString(event), e);
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
		//do nothing.
	}
}
