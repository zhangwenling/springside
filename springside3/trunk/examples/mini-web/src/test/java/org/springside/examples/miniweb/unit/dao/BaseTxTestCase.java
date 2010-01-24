package org.springside.examples.miniweb.unit.dao;

import org.junit.Before;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * 数据库访问测试基类。
 * 
 * 继承SpringTxTestCase的所有方法, 并在第一个测试方法前初始化数据.
 * 
 * @see SpringTxTestCase
 * 
 * @author calvin
 */
public class BaseTxTestCase extends SpringTxTestCase {

	private static boolean loaded = false;

	@Before
	public void loadDefaultData() throws Exception {
		if (!loaded) {
			loadDbUnitData("/data/default-data.xml");
			loaded = true;
		}
	}
}