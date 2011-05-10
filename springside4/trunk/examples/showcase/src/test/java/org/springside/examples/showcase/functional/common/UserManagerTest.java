package org.springside.examples.showcase.functional.common;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.springside.examples.showcase.functional.BaseFunctionalTestCase;

/**
 * 用户管理的功能测试.
 * 
 * @author calvin
 */
public class UserManagerTest extends BaseFunctionalTestCase {

	@Test
	public void editUser() {
		selenium.open(BASE_URL);
		selenium.clickTo(By.linkText("综合演示"));
		selenium.clickTo(By.id("editLink-2"));

		//修改用户需要登录管理员权限
		selenium.type(By.name("username"), "admin");
		selenium.type(By.name("password"), "admin");
		selenium.clickTo(By.xpath("//input[@value='登录']"));

		//点击提交按钮
		selenium.type(By.name("name"), "user_foo");
		selenium.clickTo(By.xpath("//input[@value='提交']"));
		//重新进入用户修改页面, 检查最后修改者
		selenium.clickTo(By.id("editLink-2"));
		assertEquals("user_foo", selenium.getValue(By.name("name")));
	}
}
