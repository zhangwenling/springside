package org.springside.examples.showcase.jmx.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系统运行情况统计信息.
 * 
 * @author calvin
 */
public class ServerStatistics {

	private AtomicInteger queryUsersCount = new AtomicInteger(0);//查询用户列表的次数

	public void incQueryUsersCount() {
		queryUsersCount.incrementAndGet();
	}

	public int getQueryUsersCount() {
		return queryUsersCount.get();
	}

	/**
	 * 仅用于从CompositeData转换
	 */
	public void setQueryUsersCount(int queryUsersCount) {
		this.queryUsersCount = new AtomicInteger(queryUsersCount);
	}
}
