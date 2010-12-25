package org.springside.modules.test.utils;

import java.util.concurrent.CountDownLatch;

/**
 * 多线程测试的工具类, 可以根据指定的线程数并发执行task.
 * 
 * @author badqiu
 */
public class MultiThreadTestUtils {

	/**
	 * 执行测试并等待执行结束,返回值为消耗时间
	 * 
	 * @param threadCount 线程数
	 * @param task 任务
	 * @return costTime
	 */
	public static long execute(int threadCount, final Runnable task) throws InterruptedException {
		CountDownLatch endSignal = execute0(threadCount, task);
		long startTime = System.currentTimeMillis();
		endSignal.await();
		return System.currentTimeMillis() - startTime;
	}

	private static CountDownLatch execute0(int threadCount, final Runnable task) throws InterruptedException {
		final CountDownLatch startSignal = new CountDownLatch(threadCount);
		final CountDownLatch endSignal = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						startSignal.countDown();
						startSignal.await();
						task.run();
					} catch (InterruptedException e) {
						//ignore
					} finally {
						endSignal.countDown();
					}
				}
			};
			t.start();
		}

		startSignal.await();
		return endSignal;
	}
}
