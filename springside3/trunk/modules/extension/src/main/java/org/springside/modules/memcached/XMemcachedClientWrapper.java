/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: MemcachedSimulator.java 1255 2010-10-13 17:52:16Z calvinxiu $
 */
package org.springside.modules.memcached;

import java.util.Collection;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * XmemcachedClient的Wrapper,做些泛型类型转换,屏蔽Checked Exception之类的杂事.
 * 
 * @author Neway
 * @author Calvin
 */
public class XMemcachedClientWrapper {

	private static Logger logger = LoggerFactory.getLogger(XMemcachedClientWrapper.class);

	private MemcachedClient memcachedClient;

	/**
	 * Get方法, 转换结果类型,失败时屏蔽异常只返回null.
	 */
	public <T> T get(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (Exception e) {
			handleException(e, key);
			return null;
		}
	}

	/**
	 * GetBulk方法, 转换结果类型, 失败时屏蔽异常只返回null.
	 */
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) memcachedClient.get(keys);
		} catch (Exception e) {
			handleException(e, StringUtils.join(keys, ","));
			return null;
		}
	}

	/**
	 * Set方法, 不等待操作返回结果, 失败抛出RuntimeException..
	 */
	public void asyncSet(String key, int expiredTime, Object value) {
		try {
			memcachedClient.setWithNoReply(key, expiredTime, value);
		} catch (Exception e) {
			handleException(e, key);
		}
	}

	/**
	 * Set方法,等待操作返回结果,失败抛出RuntimeException..
	 */
	public boolean set(String key, int expiredTime, Object value) {
		try {
			return memcachedClient.set(key, expiredTime, value);
		} catch (Exception e) {
			throw handleException(e, key);
		}
	}

	/**
	 * Delete方法, 失败抛出RuntimeException.
	 */
	public boolean delete(String key) {
		try {
			return memcachedClient.delete(key);
		} catch (Exception e) {
			throw handleException(e, key);
		}
	}

	/**
	 * Incr方法, 失败抛出RuntimeException.
	 */
	public long incr(String key, int by, long defaultValue) {
		try {
			return memcachedClient.incr(key, by, defaultValue);
		} catch (Exception e) {
			throw handleException(e, key);
		}
	}

	/**
	 * Decr方法, 失败RuntimeException.
	 */
	public long decr(String key, int by, long defaultValue) {
		try {
			return memcachedClient.decr(key, by, defaultValue);
		} catch (Exception e) {
			throw handleException(e, key);
		}
	}

	private RuntimeException handleException(Exception e, String key) {
		logger.warn("xmemcached client receive a exception with key:" + key, e);
		return new RuntimeException(e);
	}

	public MemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	@Autowired(required = false)
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
}
