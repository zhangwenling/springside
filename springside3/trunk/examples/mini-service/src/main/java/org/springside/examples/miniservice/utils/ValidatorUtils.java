package org.springside.examples.miniservice.utils;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class ValidatorUtils {

	/**
	 * 转换所有violations信息.
	 */
	public static String convertMessage(Set<? extends ConstraintViolation> constraintViolations, String separator) {
		List<String> errorMessages = Lists.newArrayList();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.add(violation.getMessage());
		}
		return StringUtils.join(errorMessages, separator);
	}
}
