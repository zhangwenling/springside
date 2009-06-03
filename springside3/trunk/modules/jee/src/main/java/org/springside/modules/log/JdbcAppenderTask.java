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
 * 将Queue中的Event写入数据库的Appender.
 * 
 * @author calvin
 */
public class JdbcAppenderTask extends QueueConsumerTask {

	protected SimpleJdbcTemplate jdbcTemplate;
	protected String sql;
	protected int bufferSize = 10;

	protected List<LoggingEvent> eventBuffer = new ArrayList<LoggingEvent>();
	protected List<LoggingEvent> eventBatch = new ArrayList<LoggingEvent>();

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
	 * 批量执行的队列大小.
	 */
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	/**
	 * @see QueueConsumerTask#processEvent(Object)
	 */
	@Override
	protected void processEvent(Object eventObject) throws InterruptedException {
		LoggingEvent event = (LoggingEvent) eventObject;
		eventBuffer.add(event);
		logger.debug("get event, {}", Log4jUtils.convertEventToString(event));

		if (eventBuffer.size() >= bufferSize) {
			updateBatch();
		}
	}

	/**
	 * @see QueueConsumerTask#clean()
	 */
	@Override
	protected void clean() {
		if (eventBuffer.size() > 0) {
			updateBatch();
		}
		logger.debug("cleaned task {}", this);
	}

	/**
	 * 批量更新Buffer中的事件.
	 */
	protected void updateBatch() {
		List<Map<String, Object>> paramMapList = new ArrayList<Map<String, Object>>();
		for (LoggingEvent event : eventBuffer) {
			Map<String, Object> paramMap = parseEvent(event);
			paramMapList.add(paramMap);
			eventBatch.add(event);
		}

		Map[] paramMapArray = paramMapList.toArray(new Map[paramMapList.size()]);
		SqlParameterSource[] paramMapBatch = SqlParameterSourceUtils.createBatch(paramMapArray);
		
		try {
			jdbcTemplate.batchUpdate(getActualSql(paramMapList), paramMapBatch);
		} catch (DataAccessException e) {
			dataAccessExceptionHandle(e, eventBatch);
		}

		eventBuffer.removeAll(eventBatch);
		if (logger.isDebugEnabled()) {
			for (LoggingEvent event : eventBatch) {
				logger.debug("saved event, {}", Log4jUtils.convertEventToString(event));
			}
		}
		eventBatch.clear();
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
	protected String getActualSql(List<Map<String, Object>> paramMapList) {
		return sql;
	}

	/**
	 * 可被子类重载的事件分析函数,可进行进一步的分析工作,如对message字符串进行分解等.
	 */
	protected void postParseEvent(LoggingEvent event, Map<String, Object> paramMap) {
	}
}
