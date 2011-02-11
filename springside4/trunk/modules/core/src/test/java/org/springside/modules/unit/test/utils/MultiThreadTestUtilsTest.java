package org.springside.modules.unit.test.utils;

import junit.framework.TestCase;

import org.springside.modules.test.utils.MultiThreadTestUtils;

public class MultiThreadTestUtilsTest extends TestCase {

	public void testexecuteAndWaitForDone() throws InterruptedException {
		long costTime = MultiThreadTestUtils.execute(2000, new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		System.out.println("costTime:" + costTime);
		assertTrue(costTime > 0);
	}
}
