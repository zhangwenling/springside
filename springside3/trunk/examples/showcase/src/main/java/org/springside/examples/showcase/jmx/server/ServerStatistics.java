package org.springside.examples.showcase.jmx.server;

import java.beans.ConstructorProperties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系统运行统计信息.
 * 
 * @author calvin
 */
public class ServerStatistics {

	private AtomicInteger queryUserCount = new AtomicInteger(0);//查询用户的次数
	private AtomicInteger modifyUserCount = new AtomicInteger(0);//修改用户的次数

	public ServerStatistics() {
	}

	/**
	 * 用于从CompositeData构造ServerStatistics实例的构造函数
	 */
	@ConstructorProperties( { "queryUserCount", "modifyUserCount" })
	public ServerStatistics(int queryUserCount, int modifyUserCount) {
		this.queryUserCount = new AtomicInteger(queryUserCount);
		this.modifyUserCount = new AtomicInteger(modifyUserCount);
	}

	public void incQueryUserCount() {
		queryUserCount.incrementAndGet();
	}

	public int getQueryUserCount() {
		return queryUserCount.get();
	}

	public void incModifyUserCount() {
		modifyUserCount.incrementAndGet();
	}

	public int getModifyUserCount() {
		return modifyUserCount.get();
	}
}
