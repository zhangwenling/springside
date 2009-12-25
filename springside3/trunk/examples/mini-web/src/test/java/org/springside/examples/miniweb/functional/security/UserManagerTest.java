package org.springside.examples.miniweb.functional.security;

import org.junit.Test;
import org.springside.examples.miniweb.data.DataUtils;
import org.springside.examples.miniweb.data.SecurityEntityData;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.functional.BaseSeleniumTestCase;
import org.springside.modules.test.groups.Groups;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseSeleniumTestCase {

	private static String commonLoginName = null;

	/**
	 * 创建公共测试用户.
	 */
	@Test
	public void createUser() {
		//打开新增用户页面
		openOverviewPage();
		clickLink("增加新用户");

		//生成待输入的测试用户数据
		User user = SecurityEntityData.getRandomUser();
		String loginName = user.getLoginName();

		//输入数据
		selenium.type("loginName", user.getLoginName());
		selenium.type("name", user.getName());
		selenium.type("password", user.getPassword());
		selenium.type("passwordConfirm", user.getPassword());
		selenium.click("checkedRoleIds-2");

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		findUser(loginName);
		assertEquals(loginName, selenium.getTable("contentTable.1.1"));
		assertEquals("用户", selenium.getTable("contentTable.1.3"));

		//设置公共测试用户名
		commonLoginName = loginName;
	}

	/**
	 * 修改公共测试用户.
	 */
	@Test
	public void editUser() {
		//确保已创建公共测试用户.
		assertCommonUserExist();

		//打开公共测试用户修改页面.
		openOverviewPage();
		findUser(commonLoginName);
		clickLink("修改");

		//更改用户名
		String newUserName = DataUtils.random("User");
		selenium.type("name", newUserName);
		//取消用户角色,增加管理员角色
		selenium.click("checkedRoleIds-1");
		selenium.click("checkedRoleIds-2");

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		findUser(commonLoginName);
		assertEquals(newUserName, selenium.getTable("contentTable.1.1"));
		assertEquals("管理员", selenium.getTable("contentTable.1.3"));
	}

	/**
	 * 删除公共用户.
	 */
	@Test
	public void deleteUser() {
		//确保已创建公共测试用户.
		assertCommonUserExist();

		//查找公共测试用户
		openOverviewPage();
		findUser(commonLoginName);

		//删除用户
		clickLink("删除");

		//校验结果
		assertTrue(selenium.isTextPresent("删除用户成功"));
		findUser(commonLoginName);
		assertFalse(selenium.isTextPresent(commonLoginName));

		//清空公共测试用户名
		commonLoginName = null;
	}

	/**
	 * 创建用户时的输入校验测试. 
	 */
	@Test
	@Groups("extension")
	public void validateUser() {
		openOverviewPage();
		clickLink("增加新用户");

		selenium.type("loginName", "admin");
		selenium.type("name", "");
		selenium.type("password", "a");
		selenium.type("passwordConfirm", "abc");
		selenium.type("email", "abc.com");
		selenium.click(SUBMIT_BUTTON);

		selenium.waitForCondition("selenium.isTextPresent('用户登录名已存在')", "5000");
		assertTrue(selenium.isTextPresent("用户登录名已存在"));
		assertEquals("必选字段", selenium.getTable("//form[@id='inputForm']/table.1.1"));
		assertEquals("请输入一个长度最少是 3 的字符串", selenium.getTable("//form[@id='inputForm']/table.2.1"));
		assertEquals("输入与上面相同的密码", selenium.getTable("//form[@id='inputForm']/table.3.1"));
		assertEquals("请输入正确格式的电子邮件", selenium.getTable("//form[@id='inputForm']/table.4.1"));
	}

	private void openOverviewPage() {
		clickMenu("帐号列表");
	}

	/**
	 * 根据用户名查找用户的工具函数. 
	 */
	private void findUser(String loginName) {
		selenium.type("filter_EQS_loginName", loginName);
		selenium.click(SEARCH_BUTTON);
		waitPageLoad();
	}

	/**
	 * 确保公共测试用户已初始化的工具函数.
	 */
	private void assertCommonUserExist() {
		if (commonLoginName == null) {
			createUser();
		}
	}
}
