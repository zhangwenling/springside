#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.functional;

import org.junit.Before;
import org.springside.modules.test.selenium.SeleniumTestCase;

/**
 * 项目相关的SeleniumTestCase基类,定义服务器地址,浏览器类型及登陆函数. 
 * 
 * @author calvin
 */
public abstract class BaseSeleniumTestCase extends SeleniumTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		login();
	}

	protected void login() {
		selenium.open("/${artifactId}/login.action");
		selenium.type("j_username", "admin");
		selenium.type("j_password", "admin");
		selenium.click("//input[@value='登录']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("你好,admin."));
	}
}
