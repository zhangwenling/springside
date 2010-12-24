/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.memcached;

import net.rubyeye.xmemcached.utils.AddrUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.thimbleware.jmemcached.CacheImpl;
import com.thimbleware.jmemcached.LocalCacheElement;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.CacheStorage;
import com.thimbleware.jmemcached.storage.hash.ConcurrentLinkedHashMap;

/**
 * JMemcached的封装, 主要用于功能测试.
 * 
 * @author calvin
 */
public class MemcachedSimulator implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(MemcachedSimulator.class);

	private MemCacheDaemon<LocalCacheElement> jmemcached;

	private String serverUrl = "localhost:11211";
	private boolean binary = false;

	private int maxItems = 1024;
	private long maxBytes = 1024 * 2048;

	public void afterPropertiesSet() throws Exception {

		logger.info("Initializing JMemcached Server");

		jmemcached = new MemCacheDaemon<LocalCacheElement>();

		CacheStorage<String, LocalCacheElement> storage = ConcurrentLinkedHashMap.create(
				ConcurrentLinkedHashMap.EvictionPolicy.FIFO, maxItems, maxBytes);
		jmemcached.setCache(new CacheImpl(storage));

		jmemcached.setAddr(AddrUtil.getAddresses(serverUrl).get(0));
		jmemcached.setBinary(binary);

		jmemcached.start();

		logger.info("Initialized JMemcached Server");
	}

	public void destroy() throws Exception {
		logger.info("Shutting down Jmemcached Server");
		jmemcached.stop();
		logger.info("Shutted down Jmemcached Server");
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setBinary(boolean binary) {
		this.binary = binary;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public void setMaxBytes(long maxBytes) {
		this.maxBytes = maxBytes;
	}
}
