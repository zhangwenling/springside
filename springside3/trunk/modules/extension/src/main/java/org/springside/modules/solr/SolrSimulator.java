/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.solr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.solr.client.solrj.embedded.JettySolrRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SolrSimulator implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SolrSimulator.class);

	private JettySolrRunner jettySolrRunner;

	private String context = "/solr";
	private int port = 8983;

	public void start() {
		logger.info("Initializing Solr Server");
		jettySolrRunner = new JettySolrRunner(context, port);

		new Thread() {
			@Override
			public void run() {
				try {
					jettySolrRunner.start(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		logger.info("Initialized Solr Server");
	}

	public void stop() throws Exception {
		if (jettySolrRunner != null) {
			jettySolrRunner.stop();
		}
	}

	public void waitForSolr(String coreName) throws Exception {

		// A raw term query type doesn't check the schema
		URL url = new URL("http://localhost:" + port + context + "/" + coreName
				+ "/select?q={!raw+f=junit_test_query}ping");

		Exception ex = null;
		// Wait for a total of 20 seconds: 100 tries, 200 milliseconds each
		for (int i = 0; i < 100; i++) {
			try {
				InputStream stream = url.openStream();
				stream.close();
			} catch (IOException e) {
				// e.printStackTrace();
				ex = e;
				Thread.sleep(200);
				continue;
			}

			return;
		}

		throw new RuntimeException("Jetty/Solr unresponsive", ex);
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
