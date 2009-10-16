package org.springside.modules.unit.security.utils;

import java.rmi.server.UID;
import java.util.UUID;
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
	public void noncesCompare() {
		for (int i = 0; i < 3; i++)
			System.out.println("Random Nonce        :" + NonceUtils.nextRandomHexNonce());
		for (int i = 0; i < 3; i++)
			System.out.println("Java UUID Nonce     :" + UUID.randomUUID().toString());
		for (int i = 0; i < 3; i++)
			System.out.println("Hibernate UUID Nonce:" + NonceUtils.nextUuidNonce());
		for (int i = 0; i < 3; i++)
			System.out.println("RMI UID Nonce       :" + new UID().toString());
		for (int i = 0; i < 3; i++)
			System.out.println("Timestamp Nonce     :" + NonceUtils.getCurrentTimestamp() + NonceUtils.getCounter());
		for (int i = 0; i < 3; i++)
			System.out.println("Mills Nonce         :" + NonceUtils.getCurrentMills() + NonceUtils.getCounter());
	}

	@Test
	public void getRandomNoncesConcurrent() {
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			executor.execute(new RandomNonceRequestTask());
		}
	}

	static class RandomNonceRequestTask implements Runnable {
		public void run() {
			for (int i = 0; i < 3; i++) {
				System.out.println("randomNonce   :" + CryptoUtils.hexEncode(NonceUtils.nextRandomNonce()));
			}
		}
	}

}
