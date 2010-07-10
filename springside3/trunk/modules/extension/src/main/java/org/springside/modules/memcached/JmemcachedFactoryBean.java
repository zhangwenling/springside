package org.springside.modules.memcached;

import net.spy.memcached.AddrUtil;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.thimbleware.jmemcached.Cache;
import com.thimbleware.jmemcached.MemCacheDaemon;
import com.thimbleware.jmemcached.storage.hash.LRUCacheStorageDelegate;

/**
 * 初始化与关闭JMemcached服务的工厂, 主要用于功能测试.
 * 
 * @author calvin
 */
public class JmemcachedFactoryBean implements FactoryBean<MemCacheDaemon>, InitializingBean, DisposableBean {

	private MemCacheDaemon jmemcached;

	private String serverUrl = "localhost:11211";

	private int maxItems = 1024;
	private long maxBytes = 1024 * 2048;
	private long ceilingSize = 2048;

	private boolean isBinaryProtocol = false;

	@Override
	public void afterPropertiesSet() throws Exception {

		LRUCacheStorageDelegate cacheStorage = new LRUCacheStorageDelegate(maxItems, maxBytes, ceilingSize);

		jmemcached = new MemCacheDaemon();
		jmemcached.setCache(new Cache(cacheStorage));
		jmemcached.setAddr(AddrUtil.getAddresses(serverUrl).get(0));
		jmemcached.setBinary(isBinaryProtocol);
		jmemcached.start();
	}

	@Override
	public void destroy() throws Exception {
		jmemcached.stop();
	}

	@Override
	public MemCacheDaemon getObject() throws Exception {
		return jmemcached;
	}

	@Override
	public Class<?> getObjectType() {
		return MemCacheDaemon.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
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
