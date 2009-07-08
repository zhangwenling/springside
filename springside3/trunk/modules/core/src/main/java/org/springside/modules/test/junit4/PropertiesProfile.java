package org.springside.modules.test.junit4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.test.annotation.ProfileValueSource;

/**
 * @IfProfileValue标记会根据Profile值决定是否执行测试方法,
 * 本类实现ProfileValueSource,从classpath中的 test.properties中读出Profile值.
 * 
 * @see org.springframework.test.annotation.IfProfileValue
 * @see org.springframework.test.annotation.ProfileValueSourceConfiguration
 * 
 * @author calvin
 */
public class PropertiesProfile implements ProfileValueSource {

	public static String PROPERTY_FILE = "test.properties";

	private Properties p;

	public String get(String key) {
		if (p == null) {
			try {
				p = new Properties();

				InputStream inputStream = PropertiesProfile.class.getClassLoader().getResourceAsStream(PROPERTY_FILE);

				if (inputStream != null) {
					p.load(inputStream);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		return p.getProperty(key);
	}
}
