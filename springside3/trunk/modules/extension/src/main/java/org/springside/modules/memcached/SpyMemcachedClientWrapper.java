package org.springside.modules.memcached;

import java.io.IOException;
import java.util.concurrent.Future;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 对SpyMemcached Client的二次封装.
 * 负责SpyMemcached Client的启动与关闭.
 * 
 * 提供常用的Get/Set函数并进行简便化封装,未提供封装的函数可直接调用getClient()取出SpyClient调用.
 * 
 * @author calvin
 *
 */
public class SpyMemcachedClientWrapper implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(SpyMemcachedClientWrapper.class);

	private MemcachedClient spyClient;

	private ObjectMapper mapper;

	private String memcachedNodes = "localhost:11211";

	private boolean isBinaryProtocol = false;

	private boolean isConsistentHashing = false;

	private long operationTimeout = 1000; //default value in Spy is 1000ms

	private int maxReconnectDelay = 30;//default value in Spy is 30s

	/**
	 * 直接取出SpyMemcached的Client,当Wrapper未提供Spy的函数时使用.
	 */
	public MemcachedClient getClient() throws Exception {
		return spyClient;
	}

	/**
	 * Get方法, 转换结果类型并屏蔽异常.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		try {
			return (T) spyClient.get(key);
		} catch (RuntimeException e) {
			logger.warn("Get from memcached server fail,key is" + key, e);
			return null;
		}
	}

	/**
	 * Get方法, 将JSON字符串转为结果类型.
	 */
	public <T> T getFromJson(String key, Class<T> valueClass) {
		String jsonString = get(key);
		if (jsonString != null) {
			try {
				return mapper.readValue(jsonString, valueClass);
			} catch (IOException e) {
				logger.warn("parse json string error:" + jsonString, e);
			}
		}
		return null;

	}

	/**
	 * Set方法.
	 */
	public Future<Boolean> set(String key, int expiredTime, Object value) {
		return spyClient.set(key, expiredTime, value);
	}

	/**
	 * Set方法, 将value存为JSON字符串. 
	 */
	public Future<Boolean> setToJson(String key, int expiredTime, Object value) {
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(value);
		} catch (IOException e) {
			logger.warn("write to json string error:" + value, e);
			return null;
		}

		return spyClient.set(key, expiredTime, jsonString);
	}

	/**
	 * Delete方法.	 
	 */
	public Future<Boolean> delete(String key) {
		return spyClient.delete(key);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initSpyMemcachedClient();
		initJacksonMapper();
	}

	protected void initSpyMemcachedClient() throws Exception {
		ConnectionFactoryBuilder cfb = new ConnectionFactoryBuilder();

		cfb.setFailureMode(FailureMode.Redistribute);
		cfb.setDaemon(true);
		cfb.setProtocol(isBinaryProtocol ? Protocol.BINARY : Protocol.TEXT);
		cfb.setLocatorType(isConsistentHashing ? Locator.CONSISTENT : Locator.ARRAY_MOD);
		cfb.setHashAlg(isConsistentHashing ? HashAlgorithm.KETAMA_HASH : HashAlgorithm.NATIVE_HASH);

		//operation timeout in miliseconds
		cfb.setOpTimeout(operationTimeout);

		//maximum reconnect interval in seconds
		cfb.setMaxReconnectDelay(maxReconnectDelay);

		try {
			spyClient = new MemcachedClient(cfb.build(), AddrUtil.getAddresses(memcachedNodes));
		} catch (IOException e) {
			logger.error("MemcachedClient initilization error: ", e);
			throw e;
		}
	}

	protected void initJacksonMapper() {
		mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
	}

	@Override
	public void destroy() throws Exception {
		if (spyClient != null) {
			spyClient.shutdown();
		}
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
