package org.springside.modules.memcached;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 对SpyMemcached Client的二次封装.
 * 
 * 1.建立SpyMemcached Client池,负责初始化与关闭.
 * 2.提供常用的Get/GetBulk/Set/Delete/Incr/Decr函数的封装.
 * 
 * 未提供封装的函数可直接调用getClient()取出Spy的原版MemcachedClient来使用.
 * 
 * @author calvin
 */
public class SpyMemcachedClient implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClient.class);

	private ArrayList<MemcachedClient> clientPool;

	private int poolSize = 1;

	private Random random = new Random();

	private boolean ignoreException = true;

	private String memcachedNodes = "localhost:11211";

	private boolean isBinaryProtocol = false;

	private boolean isConsistentHashing = false;

	private long operationTimeout = 1000; //default value in Spy is 1000ms

	private int maxReconnectDelay = 30;//default value in Spy is 30s

	@Override
	public void afterPropertiesSet() throws Exception {
		clientPool = new ArrayList<MemcachedClient>(poolSize);

		for (int i = 0; i < poolSize; i++) {
			ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();

			cfb.setFailureMode(FailureMode.Redistribute);
			cfb.setDaemon(true);
			cfb.setProtocol(isBinaryProtocol ? Protocol.BINARY : Protocol.TEXT);

			if (isConsistentHashing) {
				cfb.setLocatorType(Locator.CONSISTENT);
				cfb.setHashAlg(HashAlgorithm.KETAMA_HASH);
			}

			cfb.setOpTimeout(operationTimeout);
			cfb.setMaxReconnectDelay(maxReconnectDelay);

			try {
				MemcachedClient spyClient = new MemcachedClient(cfb.build(), AddrUtil.getAddresses(memcachedNodes));
				clientPool.add(spyClient);
			} catch (IOException e) {
				logger.error("MemcachedClient initilization error: ", e);
				throw e;
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		for (MemcachedClient spyClient : clientPool) {
			if (spyClient != null) {
				spyClient.shutdown();
			}
		}
	}

	/**
	 * 直接取出SpyMemcached的Client,当Wrapper未提供封装的函数时使用.
	 */
	public MemcachedClient getClient() {
		return clientPool.get(random.nextInt(poolSize));
	}

	// 封装方法 //
	/**
	 * Get方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) getClient().get(key);
		} catch (RuntimeException e) {
			if (ignoreException) {
				logger.warn("Get from memcached server fail,key is" + key, e);
				return null;
			} else {
				throw e;
			}
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(String... keys) {
		try {
			return (Map<String, T>) getClient().getBulk(keys);
		} catch (RuntimeException e) {
			if (ignoreException) {
				logger.warn("Get from memcached server fail,keys are" + keys, e);
				return null;
			} else {
				throw e;
			}
		}
	}

	/**
	 * GetBulk方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getBulk(Collection<String> keys) {
		try {
			return (Map<String, T>) getClient().getBulk(keys);
		} catch (RuntimeException e) {
			if (ignoreException) {
				logger.warn("Get from memcached server fail,keys are" + keys, e);
				return null;
			} else {
				throw e;
			}
		}
	}

	/**
	 * Set方法.
	 */
	public Future<Boolean> set(String key, int expiredTime, Object value) {
		return getClient().set(key, expiredTime, value);
	}

	/**
	 * Delete方法.	 
	 */
	public Future<Boolean> delete(String key) {
		return getClient().delete(key);
	}

	/**
	 * Incr方法.
	 */
	public long incr(String key, int by, long defaultValue) {
		return getClient().incr(key, by, defaultValue);
	}

	/**
	 * Decr方法.
	 */
	public long decr(String key, int by, long defaultValue) {
		return getClient().decr(key, by, defaultValue);
	}

	//Setter方法//

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	/**
	 *  支持多节点, 以","分割.
	 *  eg. "localhost:11211,localhost:11212"
	 */
	public void setMemcachedNodes(String memcachedNodes) {
		this.memcachedNodes = memcachedNodes;
	}

	public void setBinaryProtocol(boolean isBinaryProtocol) {
		this.isBinaryProtocol = isBinaryProtocol;
	}

	public void setConsistentHashing(boolean isConsistentHashing) {
		this.isConsistentHashing = isConsistentHashing;
	}

	public void setOperationTimeout(long operationTimeout) {
		this.operationTimeout = operationTimeout;
	}

	public void setMaxReconnectDelay(int maxReconnectDelay) {
		this.maxReconnectDelay = maxReconnectDelay;
	}
}
