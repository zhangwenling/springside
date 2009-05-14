package org.springside.modules.test.junit4;

import org.hibernate.SessionFactory;
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
	 * 刷新默认的sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 因为没有执行commit操作,不会更改测试数据库.
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
}
