package org.springside.examples.showcase.cache;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.AccountManager;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * 使用ConcurrentHashMap的本地缓存策略.
 * 
 * 基于Google Collection实现:
 * 
 * 1.加大并发锁数量.
 * 2.当key不存在时, 需要进行较长时间的计算(如访问数据库)时, 能避免并发访问造成的重复计算.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
@TransactionConfiguration(transactionManager = "defaultTransactionManager")
public class MapCacheDemo extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Autowired
	private AccountManager accountManager;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	@Test
	public void normal() {

		Function<String, User> computingFunction = new Function<String, User>() {
			@Override
			public User apply(String key) {
				User user = accountManager.getUser(key);
				return user;
			}
		};

		ConcurrentMap<String, User> userMap = new MapMaker().concurrencyLevel(32).makeComputingMap(computingFunction);

		assertEquals("Admin", userMap.get("1").getName());
	}
}
