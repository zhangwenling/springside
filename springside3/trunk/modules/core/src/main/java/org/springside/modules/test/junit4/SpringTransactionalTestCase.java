package org.springside.modules.test.junit4;

import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Spring的支持数据库事务和依赖注入的JUnit 4 TestCase基类简写.
 * 
 * @author calvin
 */
//默认载入applicationContext.xml,子类中的@ContextConfiguration定义将合并父类的定义.
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringTransactionalTestCase extends AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * @see #flush(String)
	 * 
	 * sessionFactory名默认为"sessionFactory".
	 */
	protected void flush() {
		flush("sessionFactory");
	}

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 因为没有执行commit操作,不会更改测试数据库.
	 * 
	 * @param sessionFactoryName applicationContext中sessionFactory的名称.
	 */
	protected void flush(final String sessionFactoryName) {
		((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
	}

	/**
	 * @see #randomString(int)
	 * 
	 * 长度默认为5.
	 */
	protected String randomString() {
		return randomString(5);
	}

	/**
	 * 产生包含数字和字母的随机字符串.
	 * 
	 * @param length 产生字符串长度
	 */
	protected String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	protected void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	protected void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	protected void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}
}
