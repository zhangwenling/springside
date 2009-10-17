package org.springside.modules.unit.security.utils;

import java.rmi.server.UID;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springside.modules.security.utils.NonceUtils;

public class NonceUtilsTest {
	@Test
	public void timestamp() {
		System.out.println("timestamp:" + NonceUtils.getCurrentTimestamp());
	}

	@Test
	public void noncesCompare() {
		//Random
		for (int i = 0; i < 3; i++)
			System.out.println("Random Nonce        :" + NonceUtils.nextRandomHexNonce());

		//标准UUID
		for (int i = 0; i < 3; i++)
			System.out.println("Java UUID Nonce     :" + UUID.randomUUID().toString());
		for (int i = 0; i < 3; i++)
			System.out.println("Hibernate UUID Nonce:" + NonceUtils.nextUuidNonce());
		for (int i = 0; i < 3; i++)
			System.out.println("RMI UID Nonce       :" + new UID().toString());

		//自定义UUID
		for (int i = 0; i < 3; i++) {
			System.out.println("Timestamp Nonce     :"
					+ new StringBuilder().append(NonceUtils.getIp()).append(NonceUtils.getCurrentTimestamp()).append(
							NonceUtils.getCounter()).toString());
		}

		for (int i = 0; i < 3; i++) {
			System.out.println("Mills Nonce         :"
					+ new StringBuilder().append(NonceUtils.nextRandomHexNonce(2)).append(NonceUtils.getCurrentMills())
							.append(NonceUtils.format(NonceUtils.getCounter(), 2)).toString());
		}
	}

	@Test
	public void getUuidNoncesConcurrent() throws InterruptedException {
		Runnable uuidNonceRequestTask = new Runnable() {
			public void run() {
				for (int i = 0; i < 3; i++) {
					String nonce = new StringBuilder().append(NonceUtils.getIp()).append(NonceUtils.getCurrentMills())
							.append(NonceUtils.format(NonceUtils.getCounter(), 2)).toString();
					System.out.println("Mills Nonce         :" + nonce);

				}
			}
		};

		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			executor.execute(uuidNonceRequestTask);
		}

		executor.shutdown();
		executor.awaitTermination(10000, TimeUnit.MILLISECONDS);
	}
}
