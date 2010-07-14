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
 * 初始化与关闭JMemcached服务的工厂, 主要用于功能测试.
 * 
 * @author calvin
 */
public class JmemcachedContainer implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(JmemcachedContainer.class);

	private MemCacheDaemon jmemcached;

	private String serverUrl = "localhost:11211";

	private int maxItems = 1024;
	private long maxBytes = 1024 * 2048;
	private long ceilingSize = 2048;

	private boolean isBinaryProtocol = false;

	@Override
	public void afterPropertiesSet() throws Exception {

		logger.info("Initializing JMemcached Daemon");

		LRUCacheStorageDelegate cacheStorage = new LRUCacheStorageDelegate(maxItems, maxBytes, ceilingSize);

		jmemcached = new MemCacheDaemon();
		jmemcached.setCache(new Cache(cacheStorage));
		jmemcached.setAddr(AddrUtil.getAddresses(serverUrl).get(0));
		jmemcached.setBinary(isBinaryProtocol);
		jmemcached.start();
	}

	@Override
	public void destroy() throws Exception {
		logger.info("Shutting down Jmemcached Daemon");
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

	public void setBinaryProtocol(boolean isBinaryProtocol) {
		this.isBinaryProtocol = isBinaryProtocol;
	}
}
