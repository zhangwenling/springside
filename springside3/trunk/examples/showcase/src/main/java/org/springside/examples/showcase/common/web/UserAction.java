package org.springside.examples.showcase.common.web;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户管理Action.
 * 
 * @author calvin
 */
@SuppressWarnings("serial")
//因为没有按Convention Plugin默认的Pacakge命名规则,因此用annotation重新指定Namespace.
@Namespace("/common")
public class UserAction extends ActionSupport {
	@Autowired
	private UserManager userManager;

	private List<User> allUsers;

	public List<User> getAllUsers() {
		return allUsers;
	}

	@Override
	public String execute() throws Exception {
		allUsers = userManager.getAll();
		return SUCCESS;
	}
}
