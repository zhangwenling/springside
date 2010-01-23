package org.springside.examples.miniweb.unit;

import java.io.IOException;

import org.junit.Before;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * 数据库访问测试基类。
 * 
 * 继承SpringTxTestCase的所有方法, 并在第一个测试方法前初始化内存数据库.
 * 
 * @see SpringTxTestCase
 * 
 * @author calvin
 */
public class BaseTxTestCase extends SpringTxTestCase {

	private boolean hasInit = false;

	@Before
	public void initDatabase() throws Exception {
		if (!hasInit) {
			createSchema();
			loadDefaultData();
			hasInit = true;
		}
	}

	protected void createSchema() throws IOException {
		runSql("/sql/h2/schema.sql",true);
	}

	protected void loadDefaultData() throws Exception {
		loadDbUnitData("/data/default-data.xml");
	}
}
