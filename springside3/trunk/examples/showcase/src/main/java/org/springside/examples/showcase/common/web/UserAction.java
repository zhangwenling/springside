package org.springside.examples.showcase.common.web;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.modules.web.struts2.CRUDActionSupport;

/**
 * 用户管理Action.
 * 
 * @author calvin
 */
@SuppressWarnings("serial")
//因为没有按Convention Plugin默认的Pacakge命名规则,因此用annotation重新指定Namespace.
@Namespace("/common")
@InterceptorRefs( { @InterceptorRef("paramsPrepareParamsStack") })
@Results( { @Result(name = CRUDActionSupport.RELOAD, location = "user.action", type = "redirect") })
public class UserAction extends CRUDActionSupport<User> {
	@Autowired
	private UserManager userManager;
	// 基本属性
	private User entity;
	private Long id;
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
		userManager.save(entity);
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
