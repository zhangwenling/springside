package org.springside.examples.showcase.jmx.client.service;

/**
 * Hibernate统计服务的DTO.
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
