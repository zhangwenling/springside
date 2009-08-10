package org.springside.examples.miniweb.web.security;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.service.security.SecurityManager;
import org.springside.examples.miniweb.web.CrudActionSupport;
import org.springside.modules.orm.hibernate.HibernateWebUtils;

/**
 * 角色管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 * @author calvin
 */
@SuppressWarnings("serial")
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "role.action", type = "redirect") })
public class RoleAction extends CrudActionSupport<Role> {

	@Autowired
	private SecurityManager securityManager;

	// 基本属性
	private Role entity;
	private Long id;
	private List<Role> allRoles;

	// 权限相关属性
	private List<Authority> allAuths; //全部可选权限列表
	private List<Long> checkedAuthIds;//页面中钩选的权限id列表

	// 基本属性访问函数 //

	public Role getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = securityManager.getRole(id);
		} else {
			entity = new Role();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		allRoles = securityManager.getAllRole();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		allAuths = securityManager.getAllAuthority();
		checkedAuthIds = entity.getAuthIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox 整合Role的Authorities Set.
		HibernateWebUtils.mergeByCheckedIds(entity.getAuthorities(), checkedAuthIds, Authority.class);
		securityManager.saveRole(entity);
		addActionMessage("保存角色成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		securityManager.deleteRole(id);
		addActionMessage("删除角色成功");
		return RELOAD;
	}

	// 其他属性访问函数及Action函数 //

	public List<Authority> getAllAuths() {
		return allAuths;
	}

	public List<Long> getCheckedAuthIds() {
		return checkedAuthIds;
	}

	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
		this.checkedAuthIds = checkedAuthIds;
	}
}