package org.springside.modules.test.junit38;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

/**
 * Spring的支持Test annotation扩展的JUnit3.8 TestCase基类简写,但不载入Spring AplicationContext.
 * 
 * @author calvin
 */
@TestExecutionListeners(value = {}, inheritListeners = false)
public class SpringAnnotationTestCase extends AbstractJUnit38SpringContextTests {
}
