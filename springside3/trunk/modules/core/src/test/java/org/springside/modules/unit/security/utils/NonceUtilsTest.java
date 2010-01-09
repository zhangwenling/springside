package org.springside.modules.unit.security.utils;

import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.RandomStringUtils;
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
			System.out.println("Random String       :" + RandomStringUtils.randomAlphanumeric(32));
		}
		assertEquals(32, RandomStringUtils.randomAlphanumeric(32).length());

		for (int i = 0; i < 3; i++) {
			System.out.println("Random Hex Bytes    :" + NonceUtils.randomHexString(32));
		}
		assertEquals(32, NonceUtils.randomHexString(32).length());

		for (int i = 0; i < 3; i++) {
			System.out.println("Random Int          :" + NonceUtils.randomHexInt());
		}
		assertTrue(NonceUtils.randomHexInt().length() <= 8);

		for (int i = 0; i < 3; i++) {
			System.out.println("Random Long         :" + NonceUtils.randomHexLong());
		}
		assertTrue(NonceUtils.randomHexLong().length() <= 16);

		//UUID
		for (int i = 0; i < 3; i++) {
			System.out.println("Random UUID         :" + UUID.randomUUID().toString());
		}
		assertEquals(36, UUID.randomUUID().toString().length());

		for (int i = 0; i < 3; i++) {
			System.out.println("RMI UID Nonce       :" + new UID().toString());
		}
		//about 26-27 char

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
	@SuppressWarnings("unchecked")
	public void getUuidNoncesConcurrent() throws InterruptedException {
		Callable uuidNonceRequestTask = new Callable() {
			public Object call() throws Exception {
				for (int i = 0; i < 3; i++) {
					String nonce = new StringBuilder().append(NonceUtils.randomHexInt()).append(
							NonceUtils.currentHexMills()).append(NonceUtils.format(NonceUtils.getCounter(), 2))
							.toString();
					System.out.println("Mills Nonce         :" + nonce);

				}
				return null;
			}
		};

		ExecutorService executor = Executors.newCachedThreadPool();
		List tasks = new ArrayList();
		for (int i = 0; i < 3; i++) {
			tasks.add(uuidNonceRequestTask);
		}
		executor.invokeAll(tasks);
	}
}
