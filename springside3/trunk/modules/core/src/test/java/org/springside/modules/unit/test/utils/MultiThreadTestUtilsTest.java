package org.springside.modules.unit.test.utils;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.TestCase;

import org.springside.modules.test.utils.MultiThreadTestUtils;

public class MultiThreadTestUtilsTest extends TestCase {
	private AtomicInteger executedCount = new AtomicInteger();
	int expectedCount = 2000;

	
	public void testexecuteAndWaitForDone() throws InterruptedException {
		
		long costTime = MultiThreadTestUtils.execute(expectedCount, new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("costTime:"+costTime);
		assertTrue(costTime > 0);
	}
	
	
	public void testMultiThreadPermenece() throws InterruptedException {
		Map map = new TreeMap();
		int steps = 10;
		for(int i = 1; i < 3000; i = i + steps) {
			steps = steps + (int)(steps * 0.2);
			long costTime = execute(i);
			System.out.println("threadCount:"+ i +" costTime:"+costTime+" nextStep:"+steps);
			map.put(costTime,i);
		}
		System.out.println(map);
	}

	long MAX_COUNT = 10000;
	private long execute(int threadCount) throws InterruptedException {
		final AtomicLong count = new AtomicLong(0);
		long costTime = MultiThreadTestUtils.execute(threadCount, new Runnable() {
			public void run() {
				while(true) {
					if(count.incrementAndGet() > MAX_COUNT) {
						return;
					}
					for(int i = 0; i < 50000; i++) {
					}
				}
			}
		});
		return costTime;
	}
}
