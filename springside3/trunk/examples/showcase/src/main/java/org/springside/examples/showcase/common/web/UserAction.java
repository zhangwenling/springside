package org.springside.examples.showcase.common.web;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.modules.web.struts2.CrudActionSupport;

/**
 * 用户管理Action.
 * 
 * @author calvin
 */
@SuppressWarnings("serial")
//因为没有按Convention Plugin默认的Pacakge命名规则,因此用annotation重新指定Namespace.
@Namespace("/common")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CrudActionSupport.RELOAD, location = "user.action", type = "redirect") })
public class UserAction extends CrudActionSupport<User> {
	@Autowired
	private UserManager userManager;
	// 基本属性
	private User entity;
	private Long id;
	private Integer workingVersion;
	private List<User> allUsers;

	// 基本属性访问函数 //

	public User getModel() {
		return entity;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = userManager.get(id);
		} else {
			entity = new User();
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setWorkingVersion(Integer workingVersion) {
		this.workingVersion = workingVersion;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		allUsers = userManager.getAll();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		if (workingVersion < entity.getVersion())
			throw new StaleStateException("对象已有新的版本");

		userManager.save(entity);
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		throw new UnsupportedOperationException("delete操作暂时未支持");
	}
}
