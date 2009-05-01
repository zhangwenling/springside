package org.springside.modules.log;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springside.modules.log.queue.QueueConsumerTask;

/**
 * 将Queue中的Event写入数据库的Appender.
 * 
 * @author calvin
 */
public class JdbcAppenderTask extends QueueConsumerTask {

	protected SimpleJdbcTemplate jdbcTemplate;

	protected String sql;

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

	@Override
	protected void processEvent(Object eventObject) {
		LoggingEvent event = (LoggingEvent) eventObject;
		Map<String, Object> paramMap = parseEvent(event);
		try {
			jdbcTemplate.update(getSql(), paramMap);
			logger.debug("jdbc commit success,{}", convertEventToString(event));
		} catch (DataAccessException e) {
			if (e instanceof DataIntegrityViolationException || e instanceof InvalidDataAccessApiUsageException) {
				ignore(event, e);
			} else {
				rollback(event, e);
			}
		}
	}

	/**
	 * 分析Event，建立Parameter Map,用于绑定sql中的Named Parameter.
	 */
	protected Map<String, Object> parseEvent(LoggingEvent event) {
		Map<String, Object> paramMap = Log4jUtils.convertEventToMap(event);
		postParseEvent(event, paramMap);
		return paramMap;
	}

	/**
	 * 将Event重新放入Queue中的错误处理策略.
	 */
	protected void rollback(LoggingEvent event, RuntimeException e) {
		queue.offer(event);
		logger.error("data access error,put event to queue again," + convertEventToString(event), e);
	}

	/**
	 * 忽略Event的错误处理策略.
	 */
	protected void ignore(LoggingEvent event, RuntimeException e) {
		logger.error("event is not correct, ignore it." + convertEventToString(event), e);
	}

	/**
	 * 可被子类重载的消息处理函数,可进行进一步的分析工作,如对message字符串进行分解等.
	 */
	protected void postParseEvent(LoggingEvent event, Map<String, Object> paramMap) {
	}

	/**
	 * 可被子类重载的sql提供函数,可对sql语句进行特殊处理，如日志表名带日期后缀 LOG_2009_02_31.
	 */
	public String getSql() {
		return sql;
	}

	@Override
	protected String convertEventToString(Object event) {
		return Log4jUtils.convertEventToString((LoggingEvent) event);
	}
}
