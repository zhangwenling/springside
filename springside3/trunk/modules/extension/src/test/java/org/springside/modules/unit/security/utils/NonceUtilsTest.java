package org.springside.modules.unit.security.utils;

import java.rmi.server.UID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.hibernate.id.UUIDHexGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.security.utils.NonceUtils;

public class NonceUtilsTest extends Assert {
	@Test
	public void timestamp() {
		System.out.println("timestamp:" + NonceUtils.currentTimestamp());
	}

	@Test
	public void noncesCompare() {
		//Random
		for (int i = 0; i < 3; i++) {
			System.out.println("Random String       :" + NonceUtils.randomString(32));
		}
		assertEquals(32, NonceUtils.randomString(32).length());

		for (int i = 0; i < 3; i++) {
			System.out.println("Random Hex Bytes    :" + NonceUtils.randomHexString(32));
		}
		assertEquals(32, NonceUtils.randomHexString(32).length());

		for (int i = 0; i < 3; i++) {
			System.out.println("Random Int          :" + NonceUtils.randomHexInt());
		}
		assertEquals(8, NonceUtils.randomHexInt().length());

		for (int i = 0; i < 3; i++) {
			System.out.println("Random Long         :" + NonceUtils.randomHexLong());
		}
		assertEquals(16, NonceUtils.randomHexLong().length());

		//UUID
		for (int i = 0; i < 3; i++) {
			System.out.println("Random UUID         :" + NonceUtils.randomUUID());
		}
		assertEquals(36, NonceUtils.randomUUID().length());

		for (int i = 0; i < 3; i++) {
			System.out.println("RMI UID Nonce       :" + new UID().toString());
		}
		assertEquals(27, new UID().toString().length());

		for (int i = 0; i < 3; i++) {
			System.out.println("Hibernate UUID Nonce:" + new UUIDHexGenerator().generate(null, null));
		}
		assertEquals(32, new UUIDHexGenerator().generate(null, null).toString().length());

		//组合Nonce
		for (int i = 0; i < 3; i++) {
			System.out.println("Timestamp Nonce     :"
					+ new StringBuilder().append(NonceUtils.randomHexInt()).append(NonceUtils.currentTimestamp())
							.append(NonceUtils.format(NonceUtils.getCounter(), 2)).toString());
		}
		for (int i = 0; i < 3; i++) {
			System.out.println("Mills Nonce         :"
					+ new StringBuilder().append(NonceUtils.randomHexString(4)).append(NonceUtils.currentHexMills())
							.append(NonceUtils.getCounter()).toString());
		}
	}

	@Test
	public void getUuidNoncesConcurrent() throws InterruptedException {
		Runnable uuidNonceRequestTask = new Runnable() {
			public void run() {
				for (int i = 0; i < 3; i++) {
					String nonce = new StringBuilder().append(NonceUtils.randomHexInt()).append(
							NonceUtils.currentHexMills()).append(NonceUtils.format(NonceUtils.getCounter(), 2))
							.toString();
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
