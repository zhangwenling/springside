package org.springside.modules.solr;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public abstract class MultiCoreSolrServerFactory implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(MultiCoreSolrServerFactory.class);

	private String serverUrl;

	private String coreNamePrefix;

	private int coresSize;

	private List<String> coreNameList;

	private Map<String, SolrServer> serverMap;

	/**
	 * 以传入字符串的Hash值取模取得SolrServer.
	 */
	public SolrServer getServerByHash(String seed) {
		int index = Math.abs(seed.hashCode()) % coresSize;
		return getServerByCoreName(coreNameList.get(index));
	}

	/**
	 * 随机选取SolrServer.
	 */
	public SolrServer getServerByRandom() {
		int index = new Random().nextInt(coresSize);
		return getServerByCoreName(coreNameList.get(index));
	}

	/**
	 * 以Core名称取得SolrServer.
	 */
	public SolrServer getServerByCoreName(String coreName) {
		SolrServer solrServer = serverMap.get(coreName);

		if (solrServer == null) {

			String url = serverUrl + coreName;

			CommonsHttpSolrServer commonsHttpSolrServer = null;
			try {
				commonsHttpSolrServer = new CommonsHttpSolrServer(url);
				logger.info("创建SolrServer成功,url为:{}", url);
			} catch (MalformedURLException e) {
				logger.error("创建SolrServer失败, url为:" + url);
				throw new IllegalArgumentException(e);
			}

			solrServer = commonsHttpSolrServer;
			serverMap.put(coreName, solrServer);
		}
		return solrServer;
	}

	/**
	 * 返回所有Shards名称.
	 */
	public String getShards() {
		return StringUtils.join(coreNameList, ",");
	}

	@Override
	public void afterPropertiesSet() {
		Assert.hasText(serverUrl);
		Assert.hasText(coreNamePrefix);
		Assert.isTrue(coresSize > 0);

		coreNameList = new ArrayList<String>(coresSize);
		for (int i = 1; i <= coresSize; i++) {
			coreNameList.add(String.format("%s%03d", coreNamePrefix, i));
		}

		serverMap = new ConcurrentHashMap<String, SolrServer>(coresSize);
	}

	/**
	 * 设定SolrServer的地址,格式化字符串以"/"结尾.
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl.endsWith("/") ? serverUrl : serverUrl + "/";
	}

	/**
	 * 设定Core名称的前缀.
	 */
	public void setCoreNamePrefix(String coreNamePrefix) {
		this.coreNamePrefix = coreNamePrefix;
	}

	/**
	 * 设定Core的数量.
	 */
	public void setCoresSize(int coresSize) {
		this.coresSize = coresSize;
	}
}
