package org.springside.examples.showcase.jmx.server;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系统运行情况统计信息.
 * 
 * @author calvin
 */
public class Statistics {

	private static AtomicInteger queryAllUsersCount = new AtomicInteger(0);//查询用户列表的次数

	public static void incQueryAllUsersCount() {
		queryAllUsersCount.incrementAndGet();
	}

	public static int getQueryAllUsersCount() {
		return queryAllUsersCount.get();
	}
}
