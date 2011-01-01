package org.springside.modules.utils.validator;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.utils.Asserter;

import com.google.common.collect.Lists;

/**
 * 用于持有JSR303 Validator(Hibernate Validator),使调用Validator可以当静态方法使用.
 * 
 * @author badqiu
 * @author calvin
 *
 */
public class ValidatorHolder implements DisposableBean {
	private static Validator validator;

	@Autowired
	public void setValidator(Validator v) {
		ValidatorHolder.validator = v;
	}

	public static Validator getValidator() {
		assertValidatorInjected();
		return validator;
	}

	/**
	 * JSR303的validate方法,返回ConstraintViolation组成的Set.
	 */
	public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		assertValidatorInjected();
		return getValidator().validate(object, groups);
	}

	/**
	 * 调用validate方法,验证失败将抛出ConstraintViolationException
	 */
	public static <T> void validateWithException(T object, Class<?>... groups) throws ConstraintViolationException {
		Set set = validate(object,groups);
		if(!set.isEmpty()) {
			throw new ConstraintViolationException(set);
		}
	}
	
	/**
	 * 调用validateProperty方法,返回ConstraintViolation组成的Set.
	 */
	public <T> Set<ConstraintViolation<T>> validateProperty(T object,String propertyName,Class<?>... groups) throws ConstraintViolationException {
		return getValidator().validateProperty(object, propertyName, groups);
	}	

	/**
	 * 调用validateProperty方法,验证失败将抛出ConstraintViolationException
	 */
	public <T> void validatePropertyWithException(T object,String propertyName,Class<?>... groups) throws ConstraintViolationException {
		Set set = getValidator().validateProperty(object, propertyName, groups);
		if(!set.isEmpty()) {
			throw new ConstraintViolationException(set);
		}
	}	
	
	/**
	 * 辅助方法,转换ConstraintViolationException.getConstraintViolations()为信息字符串,以seperator分割.
	 */
	public static String convertMessage(ConstraintViolationException e, String seperator) {
		return convertMessage(e.getConstraintViolations(),seperator);
	}
	
	/**
	 * 辅助方法,转换Set<ConstraintViolation>为信息字符串,以seperator分割.
	 */
	public static String convertMessage(Set<? extends ConstraintViolation> constraintViolations, String seperator) {
		List<String> errorMessages = Lists.newArrayList();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.add(violation.getMessage());
		}
		return StringUtils.join(errorMessages, seperator);

	}

	public static void cleanHolder() {
		validator = null;
	}

	@Override
	public void destroy() throws Exception {
		ValidatorHolder.cleanHolder();

	}

	private static void assertValidatorInjected() {
		Asserter.state(validator != null, "'validator' property is null,ValidatorHolder not yet init.");
	}
}
