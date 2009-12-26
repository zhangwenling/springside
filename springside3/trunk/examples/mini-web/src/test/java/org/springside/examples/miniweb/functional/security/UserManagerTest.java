package org.springside.examples.miniweb.functional.security;

import org.junit.Test;
import org.springside.examples.miniweb.data.DataUtils;
import org.springside.examples.miniweb.data.SecurityEntityData;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.functional.BaseSeleniumTestCase;
import org.springside.modules.test.groups.Groups;

/**
 * 用户管理的功能测试, 测试页面JavaScript及主要用户故事流程.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseSeleniumTestCase {

	private static User testUser = null;

	/**
	 * 检验OverViewPage.
	 */
	@Test
	public void overviewPage() {
		clickMenu(USER_MENU);

		assertEquals("admin", getTableGrid(1, OverviewColumn.LOGIN_NAME));
		assertEquals("管理员, 用户", getTableGrid(1, OverviewColumn.ROLES));
	}

	/**
	 * 创建公共测试用户.
	 */
	@Test
	public void createUser() {
		//打开新增用户页面
		clickMenu(USER_MENU);
		clickLink("增加新用户");

		//生成待输入的测试用户数据
		User user = SecurityEntityData.getRandomUser();

		//输入数据
		selenium.type("loginName", user.getLoginName());
		selenium.type("name", user.getName());
		selenium.type("password", user.getPassword());
		selenium.type("passwordConfirm", user.getPassword());

		for(Role role:user.getRoleList()){
			selenium.check(getRoleCheckId(role.getId()));
		}

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		searchUser(user.getLoginName());
		assertEquals(user.getName(), getTableGrid(1, OverviewColumn.NAME));
		assertEquals(user.getRoleNames(), getTableGrid(1, OverviewColumn.ROLES));

		//设置公共测试用户
		testUser = user;
	}

	/**
	 * 修改公共测试用户.
	 */
	@Test
	public void editUser() {
		//确保已创建公共测试用户.
		ensureTestUserExist();

		//打开公共测试用户修改页面.
		clickMenu(USER_MENU);
		searchUser(testUser.getLoginName());
		clickLink("修改");
		
		
		//校验用户页面
		assertEquals(testUser.getLoginName(), selenium.getValue("loginName"));
		assertEquals(testUser.getName(), selenium.getValue("name"));
		selenium.isChecked("checkedRoleIds-1");

		//更改用户名
		String newUserName = DataUtils.random("User");
		selenium.type("name", newUserName);
		//取消用户角色,增加管理员角色
		selenium.check(getRoleCheckId(1L));
		selenium.uncheck(getRoleCheckId(2L));
		testUser.getRoleList().clear();
		testUser.getRoleList().add(SecurityEntityData.getRole(2L));

		selenium.click(SUBMIT_BUTTON);
		waitPageLoad();

		//校验结果
		assertTrue(selenium.isTextPresent("保存用户成功"));
		searchUser(testUser.getLoginName());
		assertEquals(newUserName, getTableGrid(1, OverviewColumn.NAME));
		assertEquals(testUser.getRoleNames(), getTableGrid(1, OverviewColumn.ROLES));
	}

	/**
	 * 删除公共用户.
	 */
	@Test
	public void deleteUser() {
		//确保已创建公共测试用户.
		ensureTestUserExist();

		//查找公共测试用户
		clickMenu(USER_MENU);
		searchUser(testUser.getLoginName());

		//删除用户
		clickLink("删除");

		//校验结果
		assertTrue(selenium.isTextPresent("删除用户成功"));
		searchUser(testUser.getLoginName());
		assertFalse(selenium.isTextPresent(testUser.getLoginName()));

		//清空公共测试用户
		testUser = null;
	}

	/**
	 * 创建用户时的输入校验测试. 
	 */
	@Test
	@Groups("extension")
	public void validateUser() {
		clickMenu(USER_MENU);
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

	enum OverviewColumn {
		LOGIN_NAME, NAME, EMAIL, ROLES
	}

	/**
	 * 取得Overview页面内容.
	 */
	private static String getTableGrid(int rowIndex, OverviewColumn column) {
		return selenium.getTable("contentTable." + rowIndex + "." + column.ordinal());
	}


	private String getRoleCheckId(Long roleId) {

		return "checkedRoleIds-" + (SecurityEntityData.getRoleIdList().indexOf(roleId) + 1);
	}

	/**
	 * 根据用户名查找用户的工具函数. 
	 */
	private void searchUser(String loginName) {
		selenium.type("filter_EQS_loginName", loginName);
		selenium.click(SEARCH_BUTTON);
		waitPageLoad();
	}

	/**
	 * 确保公共测试用户已初始化的工具函数.
	 */
	private void ensureTestUserExist() {
		if (testUser == null) {
			createUser();
		}
	}
}
