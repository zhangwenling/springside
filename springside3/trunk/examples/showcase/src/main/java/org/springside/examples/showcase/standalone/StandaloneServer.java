package org.springside.examples.showcase.standalone;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StandaloneServer {
	private static Logger logger = LoggerFactory.getLogger(StandaloneServer.class);
	private final CountDownLatch latch = new CountDownLatch(1);
	private Thread shutdownHook;
	private AbstractApplicationContext applicationContext;

	public static void main(String[] args) throws Exception {
		StandaloneServer server = new StandaloneServer();
		server.registerShutdownHook();
		server.run();
	}

	public void run() throws Exception {
		start();
		waitShutdown();
		stop();
	}

	public void registerShutdownHook() {
		if (this.shutdownHook == null) {
			this.shutdownHook = new Thread() {
				public void run() {
					shutdown();
				}
			};
			Runtime.getRuntime().addShutdownHook(shutdownHook);
		}
	}

	public void shutdown() {
		latch.countDown();
	}

	protected void start() throws Exception {
		logger.info("Standalone Server  starting");

		String[] applicationContextPaths = getApplicationContextPaths();
		applicationContext = new ClassPathXmlApplicationContext(applicationContextPaths);
		applicationContext.start();

		logger.info("Standalone Server  started");
	}

	protected void waitShutdown() {

		try {
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			logger.error("Error happen at runtime", e);
		}
	}

	protected void stop() throws Exception {
		logger.info("Standalone Server  stopping");

		if (applicationContext != null) {
			applicationContext.close();
		}
		Runtime.getRuntime().removeShutdownHook(shutdownHook);
		logger.info("Standalone Server  stopped");
	}

	protected String[] getApplicationContextPaths() {
		return new String[] { "applicationContext.xml" };
	}
}
