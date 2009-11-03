package org.springside.modules.unit.spring;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.spring.SpringContextHolder;
import org.springside.modules.utils.ReflectionUtils;

public class SpringContextHolderTest extends Assert {

	@Test
	public void testGetBean() {
		
		ReflectionUtils.setFieldValue(new SpringContextHolder(), "applicationContext", null);
		
		try{
			SpringContextHolder.getBean("julToSlf4jHandler");	
			fail("No exception throw for applicationContxt hadn't been init.");
		}catch (IllegalStateException e){
			
		}
		
		new ClassPathXmlApplicationContext("classpath:/applicationContext-test.xml");
		assertNotNull(SpringContextHolder.getApplicationContext());
		assertNotNull(SpringContextHolder.getBean("julToSlf4jHandler"));
		assertNotNull(SpringContextHolder.getBean(SpringContextHolder.class));
	}
	
}
