package org.springside.modules.log;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 在List中保存日志的Appender,用于测试日志输出.
 * 
 * @author calvin
 */
public class InMemoryAppender extends AppenderSkeleton {

	private List<LoggingEvent> logs = new ArrayList<LoggingEvent>();

	@Override
	protected void append(LoggingEvent event) {
		logs.add(event);
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

	public List<LoggingEvent> getLogs() {
		return logs;
	}
}
