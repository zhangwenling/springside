package org.springside.modules.test.junit4;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Spring的支持依赖注入的JUnit 4 TestCase基类简写.
 * 
 * @author calvin
 */

//默认载入applicationContext.xml,子类中的@ContextConfiguration定义将合并父类的定义.
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SpringContextTestCase extends AbstractJUnit4SpringContextTests {
}
