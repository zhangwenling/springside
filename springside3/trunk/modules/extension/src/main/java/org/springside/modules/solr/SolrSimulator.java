/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.solr;

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

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Initializing Solr Server");
		jettySolrRunner = new JettySolrRunner(context, port);
		new Thread() {
			@Override
			public void run() {
				try {
					jettySolrRunner.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
		jettySolrRunner.waitForSolr(context);
		logger.info("Initialized Solr Server");
	}

	@Override
	public void destroy() throws Exception {
		if (jettySolrRunner != null) {
			jettySolrRunner.stop();
		}
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
