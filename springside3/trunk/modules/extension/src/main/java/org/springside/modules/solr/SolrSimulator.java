/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.solr;

import org.apache.solr.client.solrj.embedded.JettySolrRunner.Servlet404;
import org.apache.solr.servlet.SolrDispatchFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springside.modules.utils.ThreadUtils;

/**
 * 参考@{JettySolrRunner},编写Solr服务端模拟器
 * 
 * @author calvin
 */
public class SolrSimulator implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SolrSimulator.class);

	private Server server;

	private String context = "/solr";
	private int port = 8983;

	public void start() {
		logger.info("Starting Solr Server");

		init();
		startInNewThread();
		waitForSolr();

		logger.info("Started Solr Server");
	}

	public void stop() throws Exception {
		logger.info("Stoping Solr Server");

		if (server != null && server.isRunning()) {
			server.stop();
			server.join();
		}
	}

	private void init() {
		server = new Server(port);
		server.setStopAtShutdown(true);

		// Initialize the servlets
		ServletContextHandler root = new ServletContextHandler(server, context, ServletContextHandler.SESSIONS);

		// for some reason, there must be a servlet for this to get applied
		root.addServlet(Servlet404.class, "/*");
		root.addFilter(SolrDispatchFilter.class, "*", 0);
	}

	private void startInNewThread() {
		new Thread() {
			@Override
			public void run() {
				try {
					if (!server.isRunning()) {
						server.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 最多等待10秒
	 */
	private void waitForSolr() {
		for (int i = 0; i < 50; i++) {
			if (server.isStarted()) {
				return;
			} else {
				ThreadUtils.sleep(200);
			}
		}

		throw new RuntimeException("Start Solr Server timeout");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		start();
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
