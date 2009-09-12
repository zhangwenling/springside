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
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springside.modules.queue.QueueConsumerTask;

/**
 * 将Queue中的log4j event写入数据库的消费者任务.
 * 定期获取的读取Queue中的消息并使用Jdbc批量写入.
 * 
 * @see QueueConsumerTask
 * 
 * @author calvin
 */
public class JdbcPeriodFetchAppenderTask extends QueueConsumerTask {

	protected SimpleJdbcTemplate jdbcTemplate;
	protected String sql;

	protected int batchSize = 10;
	protected int period = 1000;

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
	* 批量定时读取消息的队列大小, 默认为10.
	*/
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * 批量定时读取的时间间隔,单位为毫秒,默认为1秒.
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * 线程执行函数,定期批量获取消息并调用processMessageList()处理.
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				List list = new ArrayList(batchSize);
				queue.drainTo(list, batchSize);
				processMessageList(list);
				Thread.sleep(period);
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断");
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
	}
}