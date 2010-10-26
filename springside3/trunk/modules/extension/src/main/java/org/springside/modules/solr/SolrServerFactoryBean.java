package org.springside.modules.solr;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * SolrServer与Spring的简单集成.
 * 
 * @author calvin
 */
public class SolrServerFactoryBean implements FactoryBean<SolrServer>,
		InitializingBean {

	private static Logger logger = LoggerFactory
			.getLogger(SolrServerFactoryBean.class);

	private String serverUrl;

	private long connectionTimeout;

	private CommonsHttpSolrServer solrServer;

	@Override
	public void afterPropertiesSet() {
		Assert.hasText(serverUrl);
		try {
			solrServer = new CommonsHttpSolrServer(serverUrl);
			solrServer.setRequestWriter(new BinaryRequestWriter());

			if (connectionTimeout > 0) {
				solrServer.setConnectionManagerTimeout(connectionTimeout);
			}
		} catch (MalformedURLException e) {
			logger.error("Solr server init error", e);
		}
	}

	@Override
	public SolrServer getObject() throws Exception {
		return solrServer;
	}

	@Override
	public Class<?> getObjectType() {
		return SolrServer.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

}
