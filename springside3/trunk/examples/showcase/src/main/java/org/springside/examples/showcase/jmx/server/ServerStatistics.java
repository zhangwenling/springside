package org.springside.examples.showcase.jmx.server;

import java.beans.ConstructorProperties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系统运行情况统计信息.
 * 
 * @author calvin
 */
public class ServerStatistics {

	private  AtomicInteger queryAllUsersCount = new AtomicInteger(0);//查询用户列表的次数

	public ServerStatistics() {
	}

	/**
	 * 用于从CompositeData构造ServerStatistics实例的构造函数
	 */
	@ConstructorProperties({"queryAllUsersCount"})
	public ServerStatistics(int queryAllUsersCount) {
		this.queryAllUsersCount = new AtomicInteger(queryAllUsersCount);
	}
	
	public  void incQueryAllUsersCount() {
		queryAllUsersCount.incrementAndGet();
	}

	public  int getQueryAllUsersCount() {
		return queryAllUsersCount.get();
	}
}
