package org.springside.modules.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Log4j 工具类, 转换Logging Event到字符串或Map.
 * 
 * @author calvin
 */
public class Log4jUtils {

	public static final String MESSAGE = "message";
	public static final String LEVEL = "level";
	public static final String TIMESTAMP = "timestamp";
	public static final String LOGGER_NAME = "loggerName";
	public static final String THREAD_NAME = "threadName";

	public static final PatternLayout DEFAULT_PATTERN = new PatternLayout("%d [%t] %-5p %c - %m");

	public static String convertEventToString(LoggingEvent event) {
		return DEFAULT_PATTERN.format(event);
	}

	public static String convertEventToString(LoggingEvent event, String pattern) {
		return new PatternLayout(pattern).format(event);
	}

	public static Map<String, Object> convertEventToMap(LoggingEvent event) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(MESSAGE, event.getMessage());
		map.put(LEVEL, event.getLevel().toString());
		map.put(LOGGER_NAME, event.getLoggerName());
		map.put(THREAD_NAME, event.getThreadName());
		map.put(TIMESTAMP, new Date(event.getTimeStamp()));
		return map;
	}
}
