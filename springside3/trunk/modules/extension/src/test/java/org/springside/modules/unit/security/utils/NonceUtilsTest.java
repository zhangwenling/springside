package org.springside.modules.unit.security.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springside.modules.security.utils.CryptoUtils;
import org.springside.modules.security.utils.NonceUtils;

public class NonceUtilsTest {
	@Test
	public void timestamp() {
		System.out.println("timestamp:" + NonceUtils.getCurrentTimestamp());
	}

	@Test
	public void getRandomNonce() {
		System.out.println("randomNonce   :" + CryptoUtils.hexEncode(NonceUtils.nextRandomNonce()));
	}

	@Test
	public void getTimestampNonce() {
		System.out.println("timestampNonce:" + NonceUtils.nextTimestampNonce());
	}

	@Test
	public void getUuidNonce() {
		for (int i = 0; i < 5; i++)
			System.out.println("UUID Nonce    :" + NonceUtils.nextUuidNonce());
	}

	@Test
	public void getRandomNoncesConcurrent() {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			executor.execute(new RandomNonceRequestTask());
		}
	}

	@Test
	public void getTimestampNoncesConcurrent() {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			executor.execute(new TimestampNonceRequestTask());
		}
	}

	static class RandomNonceRequestTask implements Runnable {
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println("randomNonce   :" + CryptoUtils.hexEncode(NonceUtils.nextRandomNonce()));
			}
		}
	}

	static class TimestampNonceRequestTask implements Runnable {
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println("timestampNonce:" + NonceUtils.nextTimestampNonce());
			}
		}
	}
}
