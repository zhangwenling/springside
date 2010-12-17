/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package org.springside.modules.memcached;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Neway
 */
public class XMemcachedClientWrapper {

    private static Logger    logger = LoggerFactory.getLogger(XMemcachedClientWrapper.class);

    private XMemcachedClient xmemcachedClient;

    /**
     * Get方法, 转换结果类型并屏蔽异常, 没有Key返回Null.
     */
    public <T> T get(String key) {
        try {
            return (T) xmemcachedClient.get(key);
        } catch (TimeoutException e) {
            logger.warn("Get from memcached server fail, key is " + key, e);
            return null;
        } catch (InterruptedException e) {
            logger.warn("Get from memcached server fail, key is " + key, e);
            return null;
        } catch (MemcachedException e) {
            logger.warn("Get from memcached server fail, key is " + key, e);
            return null;
        }
    }

    /**
     * GetBulk方法, 转换结果类型并屏蔽异常.
     */
    public <T> Map<String, T> getBulk(Collection<String> keys) {
        try {
            return (Map<String, T>) xmemcachedClient.get(keys);
        } catch (TimeoutException e) {
            logger.warn("Get from memcached server fail, keys are " + keys, e);
            return null;
        } catch (InterruptedException e) {
            logger.warn("Get from memcached server fail,keys are " + keys, e);
            return null;
        } catch (MemcachedException e) {
            logger.warn("Get from memcached server fail,keys are " + keys, e);
            return null;
        }
    }

    /**
     * Set方法，不等待操作返回结果.
     */
    public void asyncSet(String key, int expiredTime, Object value) {
        try {
            xmemcachedClient.setWithNoReply(key, expiredTime, value);
        } catch (InterruptedException e) {
            logger.warn("Set to memcached server fail,key is " + key, e);
        } catch (MemcachedException e) {
            logger.warn("Set to memcached server fail,key is " + key, e);
        }
    }

    /**
     * Set方法.
     */
    public boolean set(String key, int expiredTime, Object value) {
        try {
            return xmemcachedClient.set(key, expiredTime, value);
        } catch (TimeoutException e) {
            logger.warn("Set to memcached server fail,key is " + key, e);
            return false;
        } catch (InterruptedException e) {
            logger.warn("Set to memcached server fail,key is " + key, e);
            return false;
        } catch (MemcachedException e) {
            logger.warn("Set to memcached server fail,key is " + key, e);
            return false;
        }
    }

    /**
     * Delete方法.
     */
    public boolean delete(String key) {
        try {
            return xmemcachedClient.delete(key);
        } catch (TimeoutException e) {
            logger.warn("Delete from memcached server fail,key is " + key, e);
            return false;
        } catch (InterruptedException e) {
            logger.warn("Delete from memcached server fail,key is " + key, e);
            return false;
        } catch (MemcachedException e) {
            logger.warn("Delete from memcached server fail,key is " + key, e);
            return false;
        }
    }

    /**
     * 配合Check and Set的Get方法, 转换结果类型并屏蔽异常.
     */
    public GetsResponse<Object> gets(String key) {
        try {
            return xmemcachedClient.gets(key);
        } catch (TimeoutException e) {
            logger.warn("Get from memcached server fail,key is" + key, e);
            return null;
        } catch (InterruptedException e) {
            logger.warn("Get from memcached server fail,key is" + key, e);
            return null;
        } catch (MemcachedException e) {
            logger.warn("Get from memcached server fail,key is" + key, e);
            return null;
        }
    }

    /**
     * Check and Set方法.
     */
    public boolean cas(String key, long casId, Object value, int exp) {
        try {
            return xmemcachedClient.cas(key, exp, value, casId);
        } catch (TimeoutException e) {
            logger.warn("set to memcached server fail,key is" + key, e);
            return false;
        } catch (InterruptedException e) {
            logger.warn("set to memcached server fail,key is" + key, e);
            return false;
        } catch (MemcachedException e) {
            logger.warn("set to memcached server fail,key is" + key, e);
            return false;
        }
    }

    /**
     * Incr方法, 失败返回-1.
     */
    public long incr(String key, int by, long defaultValue) {
        try {
            return xmemcachedClient.incr(key, by, defaultValue);
        } catch (TimeoutException e) {
            logger.warn("Increase memcached value fail,key is " + key, e);
            return -1;
        } catch (InterruptedException e) {
            logger.warn("Increase memcached value fail,key is " + key, e);
            return -1;
        } catch (MemcachedException e) {
            logger.warn("Increase memcached value fail,key is " + key, e);
            return -1;
        }
    }

    /**
     * Decr方法, 失败返回-1.
     */
    public long decr(String key, int by, long defaultValue) {
        try {
            return xmemcachedClient.decr(key, by, defaultValue);
        } catch (TimeoutException e) {
            logger.warn("Decrease memcached value fail,key is " + key, e);
            return -1;
        } catch (InterruptedException e) {
            logger.warn("Decrease memcached value fail,key is " + key, e);
            return -1;
        } catch (MemcachedException e) {
            logger.warn("Decrease memcached value fail,key is " + key, e);
            return -1;
        }
    }

    /**
     * 异步Incr方法, 不支持默认值, 无返回结果.
     */
    public void asyncIncr(String key, int by) {
        try {
            xmemcachedClient.incrWithNoReply(key, by);
        } catch (InterruptedException e) {
            logger.warn("Increase memcached value fail,key is " + key, e);
        } catch (MemcachedException e) {
            logger.warn("Increase memcached value fail,key is " + key, e);
        }
    }

    /**
     * 异步Decr方法, 不支持默认值, 无返回结果.
     */
    public void asyncDecr(String key, int by) {
        try {
            xmemcachedClient.decrWithNoReply(key, by);
        } catch (InterruptedException e) {
            logger.warn("Decrease memcached value fail,key is " + key, e);
        } catch (MemcachedException e) {
            logger.warn("Decrease memcached value fail,key is " + key, e);
        }
    }

    public XMemcachedClient getxmemcachedClient() {
        return xmemcachedClient;
    }

    public void setxmemcachedClient(XMemcachedClient xmemcachedClient) {
        this.xmemcachedClient = xmemcachedClient;
    }

}
