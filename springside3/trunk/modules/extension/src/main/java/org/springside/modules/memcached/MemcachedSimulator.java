/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.memcached;

import net.spy.memcached.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.thimbleware.jmemcached.Cache;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.hash.LRUCacheStorageDelegate;

/**
 * JMemcached的封装, 主要用于功能测试.
 * 注意JMemcached对二进制协议支持不好, 尽量使用文本协议.
 * 
 * @author calvin
 */
public class MemcachedSimulator implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(MemcachedSimulator.class);

	private MemCacheDaemon jmemcached;

	private String serverUrl = "localhost:11211";

	private int maxItems = 1024;
	private long maxBytes = 1024 * 2048;
	private long ceilingSize = 2048;

	@Override
	public void afterPropertiesSet() throws Exception {

		logger.info("Initializing JMemcached Server");

		LRUCacheStorageDelegate cacheStorage = new LRUCacheStorageDelegate(maxItems, maxBytes, ceilingSize);

		jmemcached = new MemCacheDaemon();
		jmemcached.setCache(new Cache(cacheStorage));
		jmemcached.setAddr(AddrUtil.getAddresses(serverUrl).get(0));
		jmemcached.setBinary(false);
		jmemcached.start();
		logger.info("Initialized JMemcached Server");
	}

	@Override
	public void destroy() throws Exception {
		logger.info("Shutting down Jmemcached Server");
		jmemcached.stop();
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}

	public void setCeilingSize(long ceilingSize) {
		this.ceilingSize = ceilingSize;
	}
}
