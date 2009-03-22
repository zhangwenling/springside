package org.springside.modules.test.junit38;

import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractTransactionalJUnit38SpringContextTests;

/**
 * Spring的支持数据库事务和依赖注入的JUnit 3.8 TestCase基类简写.
 * 
 * @author calvin
 */
//默认载入applicationContext.xml,子类中的@ContextConfiguration定义将合并父类的定义.
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringTransactionalTestCase extends AbstractTransactionalJUnit38SpringContextTests {

	/**
	 * 刷新默认的sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 但只要不执行commit操作,SQL执行的结果不会实际提交到测试数据库中.
	 * sessionFactory名默认为"sessionFactory".
	 */
	public void flush() {
		flush("sessionFactory");
	}

	/**
	 * 刷新sessionFactory,强制Hibernate执行SQL以验证ORM配置.
	 * 但只要不执行commit操作,SQL执行的结果不会实际提交到测试数据库中.
	 * 
	 * @param sessionFactoryName applicationContext中sessionFactory的名称.
	 */
	public void flush(String sessionFactoryName) {
		((SessionFactory) applicationContext.getBean(sessionFactoryName)).getCurrentSession().flush();
	}
}
