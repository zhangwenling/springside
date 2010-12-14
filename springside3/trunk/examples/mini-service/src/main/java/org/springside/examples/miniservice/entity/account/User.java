package org.springside.examples.miniservice.entity.account;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springside.examples.miniservice.entity.IdEntity;

/**
 * 用户.
 * 
 * @author calvin
 */
public class User extends IdEntity {
	private String loginName;
	private String password;
	private String name;
	private String email;
	private Department department;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}