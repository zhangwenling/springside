package org.springside.examples.showcase.jmx.client.service;

/**
 * 封装Hibernate统计数据的DTO.
 */
public class HibernateStatistics {
	private long sessionOpenCount;
	private long sessionCloseCount;

	public long getSessionOpenCount() {
		return sessionOpenCount;
	}

	public void setSessionOpenCount(long sessionOpenCount) {
		this.sessionOpenCount = sessionOpenCount;
	}

	public long getSessionCloseCount() {
		return sessionCloseCount;
	}

	public void setSessionCloseCount(long sessionCloseCount) {
		this.sessionCloseCount = sessionCloseCount;
	}
}
