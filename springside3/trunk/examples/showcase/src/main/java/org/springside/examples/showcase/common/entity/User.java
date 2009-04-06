package org.springside.examples.showcase.common.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.examples.showcase.orm.AuditableEntity;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系.
 * 
 * @author calvin
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "USERS")
public class User extends AuditableEntity {

	private String loginName;
	private String password;
	private String name;
	private String email;

	private Set<Role> roles = new LinkedHashSet<Role>(); //有序的关联对象集合

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

	//避免定义CascadeType.REMOVE, 否则删除角色时会连带删除拥有它的用户
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	//多对多定义
	@JoinTable(name = "USERS_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	//Fecth定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序
	@OrderBy("id")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public String getRoleNames() throws Exception {
		return ReflectionUtils.fetchElementPropertyToString(roles, "name", ", ");
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<Long> getRoleIds() throws Exception {
		return ReflectionUtils.fetchElementPropertyToList(roles, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}