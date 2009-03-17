package org.springside.modules.test.junit38;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

/**
 * 支持Spring依赖注入的JUnit 3.8 TestCase基类的便捷简写.
 * 
 * @author calvin
 */
//默认载入applicationContext.xml,子类中的@ContextConfiguration定义将合并父类的定义.
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringContextTestCase extends AbstractJUnit38SpringContextTests {

}
