package org.springside.modules.test.utils;

import java.io.InputStream;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;

public class DBUnitUtils {
	/**
	 * 初始化XML数据文件到H2数据库.
	 */
	public static void loadDbUnitData(DataSource h2DataSource,String xmlPath) throws Exception {
		IDatabaseConnection connection = new H2Connection(h2DataSource.getConnection(), "");
		InputStream input = DBUnitUtils.class.getResourceAsStream(xmlPath);
		IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(input);
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
}
