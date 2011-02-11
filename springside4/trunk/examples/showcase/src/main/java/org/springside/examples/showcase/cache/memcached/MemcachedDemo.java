package org.springside.examples.showcase.cache.memcached;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.memcached.XMemcachedClientWrapper;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.google.common.collect.Lists;

@ContextConfiguration(locations = { "/cache/applicationContext-memcached.xml", "/applicationContext.xml" })
public class MemcachedDemo extends SpringContextTestCase {

    @Autowired
    private XMemcachedClientWrapper xmemcachedClientWrapper;

    @Test
    public void normal() {

        String key = "consumer:1";
        String value = "admin";

        xmemcachedClientWrapper.set(key, 60 * 60 * 1, value);

        String result = xmemcachedClientWrapper.get(key);
        assertEquals(value, result);

        xmemcachedClientWrapper.delete(key);
        result = xmemcachedClientWrapper.get(key);
        assertNull(result);
    }

    @Test
    public void getBulk() {

        String key1 = "consumer:1";
        String value1 = "admin";

        String key2 = "consumer:2";
        String value2 = "calvin";

        String key3 = "invalidKey";

        xmemcachedClientWrapper.set(key1, 60 * 60 * 1, value1);
        xmemcachedClientWrapper.set(key2, 60 * 60 * 1, value2);

        Map<String, String> result = xmemcachedClientWrapper.getBulk(Lists.newArrayList(key1, key2));
        assertEquals(value1, result.get(key1));
        assertEquals(value2, result.get(key2));
        assertNull(result.get(key3));
    }

    @Test
    public void incr() {
        String key = "incr_key";

        //注意,incr返回的数值使用long表达
        long result = xmemcachedClientWrapper.incr(key, 2, 1);
        assertEquals(1, result);
        //注意,get返回的数值使用字符串表达
        assertEquals("1", xmemcachedClientWrapper.get(key));

        result = xmemcachedClientWrapper.incr(key, 2, 1);
        assertEquals(3, result);
        assertEquals("3", xmemcachedClientWrapper.get(key));

        key = "set_and_incr_key";
        //注意,set中的数值必须使用字符串,后面的incr操作结果才会正确.
        xmemcachedClientWrapper.set(key, 60 * 60 * 1, "1");
        result = xmemcachedClientWrapper.incr(key, 2, 1);
        assertEquals(3, result);
    }
}
