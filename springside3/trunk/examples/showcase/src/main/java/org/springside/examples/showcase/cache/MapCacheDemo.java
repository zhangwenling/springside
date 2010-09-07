package org.springside.examples.showcase.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.AccountManager;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * 使用ConcurrentHashMap的本地缓存策略.
 * 
 * 基于Google Collection实现:
 * 
 * 1.加大并发锁数量.
 * 2.每个放入的对象在固定时间后过期.
 * 3.当key不存在时, 需要进行较长时间的计算(如访问数据库)时, 能避免并发访问造成的重复计算.
 * 
 * @author calvin
 */
public class MapCacheDemo {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		final AccountManager userManager = context.getBean(AccountManager.class);

		Function<String, User> computingFunction = new Function<String, User>() {
			public User apply(String key) {
				return userManager.getInitedUser("1");
			}
		};

		ConcurrentMap<String, User> userMap = new MapMaker().concurrencyLevel(32).expiration(7, TimeUnit.DAYS)
				.makeComputingMap(computingFunction);

		System.out.println(userMap.get("1").getName());
	}
}
