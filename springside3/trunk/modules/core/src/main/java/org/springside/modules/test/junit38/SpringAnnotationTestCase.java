package org.springside.modules.test.junit38;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

/**
 * 支持Spring Test annotation扩展的JUnit3.8 TestCase基类,但不载入Spring Context.
 * 
 * @author calvin
 */
@TestExecutionListeners(value = {}, inheritListeners = false)
public class SpringAnnotationTestCase extends AbstractJUnit38SpringContextTests {
}
