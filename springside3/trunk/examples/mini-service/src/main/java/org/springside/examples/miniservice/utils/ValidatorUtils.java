package org.springside.examples.miniservice.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ValidatorUtils {

	/**
	 * 转换所有violations信息,如email is abc, not a not a well-formed email address.
	 */
	public static <T> String convertMessage(Set<ConstraintViolation<T>> constraintViolations, Class<T> clazz) {
		char seperateChar = '\n';

		StringBuilder message = new StringBuilder();
		for (ConstraintViolation<T> violation : constraintViolations) {
			message.append(violation.getPropertyPath()).append(" is ").append(violation.getInvalidValue()).append(",")
					.append(violation.getMessage()).append(seperateChar);
		}
		return message.toString();
	}
}
