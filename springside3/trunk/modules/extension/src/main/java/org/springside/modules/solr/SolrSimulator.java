package org.springside.modules.solr;

import org.apache.solr.client.solrj.embedded.JettySolrRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class SolrSimulator implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SolrSimulator.class);

	private String context = "/solr";

	private int port = 8983;

	private JettySolrRunner jettySolrRunner;

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Initializing Solr Server");
		jettySolrRunner = new JettySolrRunner(context, port);
		jettySolrRunner.start(true);
		logger.info("Initialized Solr Server");
	}

	@Override
	public void destroy() throws Exception {
		if (jettySolrRunner != null) {
			jettySolrRunner.stop();
		}
	}
}
