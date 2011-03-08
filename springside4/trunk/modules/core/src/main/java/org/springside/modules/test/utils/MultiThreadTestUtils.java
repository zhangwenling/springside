package org.springside.modules.test.utils;

import java.util.concurrent.CountDownLatch;

/**
 * 多线程测试的工具类.
 * 
 * @author badqiu
 * @author calvin
 */
public abstract class MultiThreadTestUtils {

	/**
	 * 指定线程数并行的执行task并等待执行结束,返回总共消耗的时间.
	 */
	public static long execute(int threadCount, final Runnable task) throws InterruptedException {
		final CountDownLatch startSignal = new CountDownLatch(threadCount);
		final CountDownLatch endSignal = new CountDownLatch(threadCount);
		final CountDownLatch runSignal = new CountDownLatch(1);

		for (int i = 0; i < threadCount; i++) {
			Thread t = new Worker(startSignal, endSignal, runSignal, task);
			t.start();
		}

		startSignal.await();
		runSignal.countDown();
		long startTime = System.currentTimeMillis();
		endSignal.await();
		return System.currentTimeMillis() - startTime;
	}

	private static class Worker extends Thread {
		private CountDownLatch startSignal;
		private CountDownLatch endSignal;
		private CountDownLatch runSignal;
		private Runnable task;

		public Worker(CountDownLatch startSignal, CountDownLatch endSignal, CountDownLatch runSignal, Runnable task) {
			this.startSignal = startSignal;
			this.endSignal = endSignal;
			this.task = task;
		}

		public void run() {
			try {
				startSignal.countDown();
				runSignal.await();
				task.run();
			} catch (InterruptedException e) {
				//ignore
			} finally {
				endSignal.countDown();
			}
		}
	}
}
