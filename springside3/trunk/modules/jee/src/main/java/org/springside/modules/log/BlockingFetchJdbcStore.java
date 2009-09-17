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
import org.springside.modules.queue.BlockingConsumerTask;

/**
 * 将Queue中的log4j event写入数据库的消费者任务.
 * 
 * 即时阻塞的读取Queue中的事件,达到缓存上限后使用Jdbc批量写入模式.
 * 如需换为定时读取模式,继承于PeriodConsumerTask稍加改造即可.
 * 
 * @see BlockingConsumerTask
 * 
 * @author calvin
 */
public class BlockingFetchJdbcStore extends BlockingConsumerTask {

	protected String sql;
	protected int batchSize = 10;

	protected SimpleJdbcTemplate jdbcTemplate;
	protected List<LoggingEvent> eventBuffer = new ArrayList<LoggingEvent>();

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
	 * 根据注入到DataSource创建jdbcTemplate.
	 */
	@Required
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * 消息处理函数,将消息放入buffer,当buffer达到batchSize时执行批量更新函数.
	 */
	@Override
	protected void processMessage(Object message) {
		LoggingEvent event = (LoggingEvent) message;
		eventBuffer.add(event);
		logger.debug("get event, {}", AppenderUtils.convertEventToString(event));

		//已到达BufferSize则执行批量插入操作
		if (eventBuffer.size() >= batchSize) {
			updateBatch();
		}
	}

	/**
	 * 将Buffer中的事件列表批量插入数据库.
	 */
	@SuppressWarnings("unchecked")
	protected void updateBatch() {
		try {
			//分析事件列表,转换为jdbc参数.
			List<Map<String, Object>> paramMapList = new ArrayList<Map<String, Object>>();
			for (LoggingEvent event : eventBuffer) {
				Map<String, Object> paramMap = parseEvent(event);
				paramMapList.add(paramMap);
			}
			Map[] paramMapArray = paramMapList.toArray(new Map[paramMapList.size()]);
			SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(paramMapArray);

			//执行批量插入,如果失败调用失败处理函数.
			try {
				jdbcTemplate.batchUpdate(getActualSql(), batchParams);
				if (logger.isDebugEnabled()) {
					for (LoggingEvent event : eventBuffer) {
						logger.debug("saved event, {}", AppenderUtils.convertEventToString(event));
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
	@Override
	protected void clean() {
		if (!eventBuffer.isEmpty()) {
			updateBatch();
		}
		logger.debug("cleaned task {}", this);
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
