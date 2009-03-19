package org.springside.examples.showcase.jmx.server;

import java.beans.ConstructorProperties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系统运行情况统计信息.
 * 
 * @author calvin
 */
public class ServerStatistics {

	private AtomicInteger queryUsersCount = new AtomicInteger(0);//查询用户列表的次数

	public ServerStatistics() {
	}

	/**
	 * 用于从CompositeData构造ServerStatistics实例的构造函数
	 */
	@ConstructorProperties( { "queryUsersCount" })
	public ServerStatistics(int queryUsersCount) {
		this.queryUsersCount = new AtomicInteger(queryUsersCount);
	}

	public void incQueryUsersCount() {
		queryUsersCount.incrementAndGet();
	}

	public int getQueryUsersCount() {
		return queryUsersCount.get();
	}
}
