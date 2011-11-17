package org.springside.examples.showcase.utilities.asserts;

import static org.junit.Assert.*;

import org.apache.commons.lang3.Validate;
import org.junit.Assert;
import org.junit.Test;

public class AssertDemo {

	@Test
	public void asserts() {

		//not null
		try {
			String parameter = "abc";

			//not null后返回值
			String result = Validate.notNull(parameter);
			assertEquals("abc", result);

			Validate.notNull(null);
			Assert.fail();
		} catch (NullPointerException e) {

		}

		//notBlank blank
		try {
			String parameter = "abc";
			String result = Validate.notEmpty(parameter);
			assertEquals("abc", result);

			Validate.notNull(null);
			Assert.fail();

		} catch (NullPointerException e) {

		}

		//is true
		try {

			Validate.isTrue(false);
		} catch (IllegalArgumentException e) {

		}

	}
}
