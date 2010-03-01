package org.springside.modules.unit.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.utils.ReflectionUtils;
import org.springside.modules.utils.SpringContextHolder;

public class SpringContextHolderTest extends Assert {

	@Test
	public void testGetBean() {

		ReflectionUtils.setFieldValue(new SpringContextHolder(), "applicationContext", null);

		try {
			SpringContextHolder.getBean("julToSlf4jHandler");
			fail("No exception throw for applicationContxt hadn't been init.");
		} catch (IllegalStateException e) {

		}

		new ClassPathXmlApplicationContext("classpath:/applicationContext-test.xml");
		assertNotNull(SpringContextHolder.getApplicationContext());
		assertNotNull(SpringContextHolder.getBean("springContextHolder"));
		assertEquals(SpringContextHolder.class,SpringContextHolder.getBean(SpringContextHolder.class).getClass());
	}

}
