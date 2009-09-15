/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JdbcAppenderTask.java 353 2009-08-22 09:33:28Z calvinxiu
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
import org.springside.modules.queue.PeriodConsumerTask;
import org.springside.modules.queue.QueueConsumerTask;

/**
 * 将Queue中的log4j event写入数据库的消费者任务.
 * 定期获取的读取Queue中的消息并使用Jdbc批量写入.
 * 
 * @see QueueConsumerTask
 * 
 * @author calvin
 */
public class PeriodFetchJdbcStore extends PeriodConsumerTask {

	protected String sql;
	protected SimpleJdbcTemplate jdbcTemplate;

	/**
	 * 带Named Parameter的insert sql.
	 * 
	 * Named Parameter的名称见Log4jUtils中的常量定义.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 根据注入到DataSource创建jdbcTemplate.
	 */
	@Required
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * 批量消息处理函数,将事件列表批量插入数据库.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void processMessageList(List messageList) {

		List<LoggingEvent> eventList = messageList;

		try {
			//分析事件列表,转换为jdbc参数.
			List<Map<String, Object>> paramMapList = new ArrayList<Map<String, Object>>();
			for (LoggingEvent event : eventList) {
				Map<String, Object> paramMap = parseEvent(event);
				paramMapList.add(paramMap);
			}
			Map[] paramMapArray = paramMapList.toArray(new Map[paramMapList.size()]);
			SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(paramMapArray);

			//执行批量插入,如果失败调用失败处理函数.
			try {
				jdbcTemplate.batchUpdate(getActualSql(), batchParams);
				if (logger.isDebugEnabled()) {
					for (LoggingEvent event : eventList) {
						logger.debug("saved event, {}", AppenderUtils.convertEventToString(event));
					}
				}
			} catch (DataAccessException e) {
				handleDataAccessException(e, eventList);
			}
		} catch (Exception e) {
			logger.error("批量提交任务时发生错误.", e);
		}
	}

	/**
	 * 分析Event, 建立Parameter Map, 用于绑定sql中的Named Parameter.
	 */
	protected Map<String, Object> parseEvent(LoggingEvent event) {
		return AppenderUtils.convertEventToMap(event);
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
			logger.error("event insert to database error, ignore it, " + AppenderUtils.convertEventToString(event), e);
		}
	}

	/**
	 * 可被子类重载的sql提供函数,可对sql语句进行特殊处理，如日志表的表名可带日期后缀 LOG_2009_02_31.
	 */
	protected String getActualSql() {
		return sql;
	}
}
