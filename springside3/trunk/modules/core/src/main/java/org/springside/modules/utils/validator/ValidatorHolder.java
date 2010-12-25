package org.springside.modules.utils.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.beans.factory.InitializingBean;
import org.springside.modules.utils.Asserter;
/**
 * 用于持有JSR303 Validator(Hibernate Validator),使调用Validator可以当静态方法使用.
 * 
 * <pre>
 * spring配置:
 * &lt;bean class="org.springside.modules.utils.validator.ValidatorHolder">
 * 	 &lt;property name="validator" ref="validator"/>
 * &lt;/bean>
 * </pre> 
 * @author badqiu
 *
 */
public class ValidatorHolder implements InitializingBean{
	private static Validator validator;

	public void afterPropertiesSet() throws Exception {
		Asserter.state(validator != null,"not found JSR303(HibernateValidator) 'validator' for ValidatorHolder ");
	}
	
	public void setValidator(Validator v) {
		Asserter.state(validator == null,"ValidatorHolder already holded 'validator'");
		ValidatorHolder.validator = v;
	}

	public static Validator getValidator() {
		Asserter.state(validator != null,"'validator' property is null,ValidatorHolder not yet init.");
		return validator;
	}

	public static <T> Set<ConstraintViolation<T>> validate(T object,
			Class<?>... groups) {
		return getValidator().validate(object, groups);
	}

	public static <T> Set<ConstraintViolation<T>> validateProperty(T object,
			String propertyName, Class<?>... groups) {
		return getValidator().validateProperty(object, propertyName, groups);
	}

	public static <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType,
			String propertyName, Object value, Class<?>... groups) {
		return getValidator().validateValue(beanType, propertyName, value, groups);
	}

	public static final BeanDescriptor getConstraintsForClass(Class<?> clazz) {
		return getValidator().getConstraintsForClass(clazz);
	}
	
	public static final <T> T unwrap(Class<T> type) {
		return getValidator().unwrap(type);
	}
	
	public static void cleanHolder() {
		validator = null;
	}
}
