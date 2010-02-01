/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.spring;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.test.utils.DBUnitUtils;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

/**
 * Spring的支持数据库访问和依赖注入的JUnit4 集成测试基类.
 * 
 *  1.Spring Context IOC support
 *  2.Spring Transaction support 
 *  3.Spring JdbcTemplate and util functions
 *  4.Hibernate SessionFactory and some util functions
 *  5.DBUnit functions. 
 *  6.JUnit Assert functions 
 *  7.Unitils Reflection Assert funtions
 *  
 * @see AbstractTransactionalJUnit4SpringContextTests
 * @see SpringContextTestCase
 * 
 * @author calvin
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class SpringTxTestCase extends SpringContextTestCase {

	protected DataSource dataSource;

	protected SimpleJdbcTemplate jdbcTemplate;

	protected String sqlScriptEncoding;

	protected SessionFactory sessionFactory;

	//-- JdbcTemplate函数 --//
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	public void setSqlScriptEncoding(String sqlScriptEncoding) {
		this.sqlScriptEncoding = sqlScriptEncoding;
	}

	protected int countRowsInTable(String tableName) {
		return SimpleJdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
	}

	protected int deleteFromTables(String... names) {
		return SimpleJdbcTestUtils.deleteFromTables(this.jdbcTemplate, names);
	}

	protected void runSql(String sqlResourcePath, boolean continueOnError) throws DataAccessException {
		Resource resource = this.applicationContext.getResource(sqlResourcePath);
		SimpleJdbcTestUtils.executeSqlScript(this.jdbcTemplate, new EncodedResource(resource, this.sqlScriptEncoding),
				continueOnError);
	}

	//-- Hibernate函数 --//
	@Autowired(required = false)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 因为没有执行commit操作,不会更改测试数据库.
	 */
	protected void flush() {
		sessionFactory.getCurrentSession().flush();
	}

	/**
	 * 将对象从session中消除, 用于测试初对象的始化情况.
	 * 
	 */
	protected void evict(final Object entity) {
		sessionFactory.getCurrentSession().evict(entity);
	}

	//-- Unitils Assert 函数 --//
	/**
	 * 反射比较对象间的所有属性,忽略expected对象的Null对象和集合中对象的次序.
	 */
	protected void assertReflectionEquals(Object expected, Object actual) {
		ReflectionAssert.assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS,
				ReflectionComparatorMode.LENIENT_ORDER);
	}

	/**
	 * @see #assertReflectionEquals(Object, Object)
	 */
	protected void assertReflectionEquals(String message, Object expected, Object actual) {
		ReflectionAssert.assertReflectionEquals(message, expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS,
				ReflectionComparatorMode.LENIENT_ORDER);
	}

	//-- DBUnit 初始化数据函数 --//
	protected void loadDbUnitData(String xmlPath) throws Exception {
		DBUnitUtils.loadDbUnitData(dataSource, xmlPath);
	}
}
