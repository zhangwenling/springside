package org.springside.modules.unit.utils;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.utils.ReflectionUtils;

public class ReflectionUtilsTest extends Assert {

	@Test
	public void getFieldValue() {
		TestBean bean = new TestBean();
		//无需getter函数, 直接读取privateField
		assertEquals(1, ReflectionUtils.getFieldValue(bean, "privateField"));
		//绕过将publicField+1的getter函数,直接读取publicField的原始值
		assertEquals(1, ReflectionUtils.getFieldValue(bean, "publicField"));
	}

	@Test
	public void setFieldValue() {
		TestBean bean = new TestBean();
		//无需setter函数, 直接设置privateField
		ReflectionUtils.setFieldValue(bean, "privateField", 2);
		assertEquals(2, bean.inspectPrivateField());

		//绕过将publicField+1的setter函数,直接设置publicField的原始值
		ReflectionUtils.setFieldValue(bean, "publicField", 2);
		assertEquals(2, bean.inspectPublicField());
	}

	@Test
	public void invokeMethod() throws InvocationTargetException {
		TestBean bean = new TestBean();
		assertEquals("hello calvin", ReflectionUtils.invokeMethod(bean, "privateMethod", new Class[] { String.class },
				new Object[] { "calvin" }));
	}

	@Test
	public void getSuperClassGenricType() {
		assertEquals(String.class, ReflectionUtils.getSuperClassGenricType(TestBean.class));
		assertEquals(Long.class, ReflectionUtils.getSuperClassGenricType(TestBean.class, 1));

		//定义父类时无泛型定义
		assertEquals(Object.class, ReflectionUtils.getSuperClassGenricType(TestBean2.class));

		//无父类定义
		assertEquals(Object.class, ReflectionUtils.getSuperClassGenricType(TestBean3.class));

	}

	class TestBean extends ParentBean<String, Long> {
		private int privateField = 1;
		private int publicField = 1;

		public int getPublicField() {
			return publicField + 1;
		}

		public void setPublicField(int publicField) {
			this.publicField = publicField + 1;
		}

		public int inspectPrivateField() {
			return privateField;
		}

		public int inspectPublicField() {
			return publicField;
		}

		@SuppressWarnings("unused")
		private String privateMethod(String text) {
			return "hello " + text;
		}
	}

	class ParentBean<T, PK> {
	}

	@SuppressWarnings("unchecked")
	class TestBean2 extends ParentBean {
	}

	class TestBean3 {
	}
}
