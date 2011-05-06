package org.springside.examples.miniweb.web.account;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.service.account.AccountManager;
import org.springside.examples.miniweb.web.CrudActionSupport;

/**
 * 權限組管理Action.
 * 
 * 演示不分页的简单管理界面.
 * 
 * @author calvin
 */
@Namespace("/account")
@Results({ @Result(name = CrudActionSupport.RELOAD, location = "group.action", type = "redirect") })
public class GroupAction extends CrudActionSupport<Group> {

	private static final long serialVersionUID = -4052047494894591406L;

	private AccountManager accountManager;

	//-- 页面属性 --//
	private Long id;
	private Group entity;
	private List<Group> allGroupList;//權限列表
	private List<String> checkedPermissions;//页面中钩选的权限列表

	//-- ModelDriven 与 Preparable函数 --//
	@Override
	public Group getModel() {
		return entity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			entity = accountManager.getGroup(id);
		} else {
			entity = new Group();
		}
	}

	//-- CRUD Action 函数 --//
	@Override
	public String list() throws Exception {
		allGroupList = accountManager.getAllGroup();
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		checkedPermissions = entity.getPermissionList();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		entity.setPermissionList(checkedPermissions);
		//保存用户并放入成功信息.
		accountManager.saveGroup(entity);
		addActionMessage("保存權限組成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		accountManager.deleteGroup(id);
		addActionMessage("删除權限組成功");
		return RELOAD;
	}

	//-- 页面属性访问函数 --//
	/**
	 * list页面显示所有權限組列表.
	 */
	public List<Group> getAllGroupList() {
		return allGroupList;
	}

	/**
	 * input页面显示權限組拥有的權限.
	 */
	public List<String> getCheckedPermissions() {
		return checkedPermissions;
	}

	/**
	 * input页面提交權限組拥有的權限.
	 */
	public void setCheckedPermissions(List<String> checkedPermissions) {
		this.checkedPermissions = checkedPermissions;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}