package org.springside.modules.unit.utils.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringContextTestCase;
import org.springside.modules.utils.validator.ValidatorHolder;

@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class ValidatorHolderTest extends SpringContextTestCase {

	@Test
	public void validate() {
		Customer customer = new Customer();
		customer.setEmail("aaa");

		Set<ConstraintViolation<Customer>> violations = ValidatorHolder.validate(customer);
		assertEquals(2, violations.size());
		String result = ValidatorHolder.convertMessage(violations, ",");
		assertTrue(StringUtils.indexOf(result, "邮件地址格式不正确") != -1);
		assertTrue(StringUtils.indexOf(result, "姓名不能为空") != -1);
	}

	@Test
	public void validateWithException() {
		Customer customer = new Customer();
		customer.setEmail("aaa");

		try {
			ValidatorHolder.validateWithException(customer);
			Assert.fail("should throw excepion");
		} catch (ConstraintViolationException e) {
			String result = ValidatorHolder.convertMessage(e, ",");
			assertTrue(StringUtils.indexOf(result, "邮件地址格式不正确") != -1);
			assertTrue(StringUtils.indexOf(result, "姓名不能为空") != -1);
		}

	}

	private static class Customer {

		String name;

		String email;

		@NotBlank(message = "姓名不能为空")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Email(message = "邮件地址格式不正确")
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}
}
