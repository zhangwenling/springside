package org.springside.examples.miniservice.entity.account;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springside.examples.miniservice.entity.IdEntity;

import com.google.common.collect.Lists;

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

	private List<Role> roleList = Lists.newArrayList();

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

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}