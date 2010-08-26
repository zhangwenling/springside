package org.springside.modules.unit.orm.hibernate;

import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.orm.hibernate.IgnorePrefixReverseEngineeringStrategy;
import org.springside.modules.utils.ReflectionUtils;

public class IgnorePrefixReverseEngineeringStrategyTest extends Assert {

	public static class MyIgnorePrefixReverseEngineeringStrategy extends IgnorePrefixReverseEngineeringStrategy {
		public MyIgnorePrefixReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
			super(delegate);
		}

		@Override
		protected int getPrefixLength() {
			return 5;
		}
	}

	@Test
	public void normal() {

		MyIgnorePrefixReverseEngineeringStrategy strategy = new MyIgnorePrefixReverseEngineeringStrategy(null);

		String className = (String) ReflectionUtils.invokeMethod(strategy, "ignorePrefix",
				new Class[] { String.class }, new Object[] { "org.springside.TACCTHello" });
		assertEquals("org.springside.Hello", className);
	}
}
