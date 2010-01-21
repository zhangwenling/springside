package org.springside.examples.miniservice.unit.dao;

import java.io.IOException;

import org.junit.Before;
import org.springside.modules.test.spring.SpringTxTestCase;
/**
 * 
 * 
 * @see SpringTxTestCase
 * 
 * @author ehuaxio
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
