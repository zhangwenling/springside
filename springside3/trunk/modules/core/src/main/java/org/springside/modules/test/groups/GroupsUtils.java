package org.springside.modules.test.groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 实现TestNG Groups分组执行用例功能的Utils函数, 判断测试类与测试方法是否在Groups内.
 * 
 * @author freeman
 * @author calvin
 */
public class GroupsUtils {

	public static final String PROPERTY_NAME = "test.groups";
	public static final String PROPERTY_FILE = "application.test.properties";

	private static List<String> groups;

	public static boolean isTestMethodInGroups(Method testMethod) {

		if (groups == null) {
			initGroups();
		}

		if (groups.contains(Groups.ALL))
			return true;

		Groups annotationGroup = testMethod.getAnnotation(Groups.class);

		if ((annotationGroup == null) || groups.contains(annotationGroup.value()))
			return true;

		return false;
	}

	public static boolean isTestClassInGroups(Class<?> testClass) {

		if (groups == null) {
			initGroups();
		}

		if (groups.contains(Groups.ALL))
			return true;

		Groups annotationGroup = testClass.getAnnotation(Groups.class);

		if ((annotationGroup == null) || groups.contains(annotationGroup.value()))
			return true;

		return false;
	}

	/**
	 * 从系统变量或Properties文件初始化运行的groups.
	 */
	protected static void initGroups() {

		String groupsDef = getGroupsFromSystemProperty();

		//如果环境变量未定义test.groups,尝试从property文件读取.
		if (groupsDef == null) {
			groupsDef = getGroupsFromPropertyFile();
			//如果仍未定义,设为全部运行
			if (groupsDef == null) {
				groupsDef = Groups.ALL;
			}
		}

		groups = Arrays.asList(groupsDef.split(","));
	}

	/**
	 * 从环境变量读取test.groups定义, 多个group可用逗号分隔.
	 * eg.
	 * java -Dtest.groups=basic,extension
	 */
	protected static String getGroupsFromSystemProperty() {
		return System.getProperty(PROPERTY_NAME);
	}

	/**
	 * 从Classpath中的application.test.properties文件读取test.groups定义, 多个group可用逗号分隔.
	 * @return
	 */
	protected static String getGroupsFromPropertyFile() {
		Properties p;
		try {
			p = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
			return p.getProperty(PROPERTY_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
