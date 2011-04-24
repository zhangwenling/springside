package org.springside.examples.miniservice.data;

import org.springside.examples.miniservice.entity.Department;
import org.springside.examples.miniservice.entity.User;
import org.springside.modules.test.utils.DataUtils;

/**
 * 用户测试数据生成.
 * 
 * @author calvin
 */
public class AccountData {

	public static Department getRandomDepartment() {
		String departmentName = DataUtils.randomName("Department");
		User manager = getRandomUser();

		Department department = new Department();
		department.setName(departmentName);

		department.setManager(manager);
		department.getUserList().add(manager);

		return department;
	}

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");
		Department department = getDefaultDepartment();

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("123456");
		user.setEmail(userName + "@springside.org.cn");
		user.setDepartment(department);

		return user;
	}

	public static Department getDefaultDepartment() {
		Department department = new Department();
		department.setId(1L);
		return department;
	}
}
