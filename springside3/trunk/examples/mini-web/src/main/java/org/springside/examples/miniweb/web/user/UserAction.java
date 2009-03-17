package org.springside.examples.miniweb.web.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.entity.user.Role;
import org.springside.examples.miniweb.entity.user.User;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.examples.miniweb.service.user.RoleManager;
import org.springside.examples.miniweb.service.user.UserManager;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateWebUtils;
import org.springside.modules.web.struts2.CRUDActionSupport;
import org.springside.modules.web.struts2.Struts2Utils;

/**
 * 用户管理Action.
 * 
 * 使用Struts2 convention-plugin annotation定义Action参数.
 * 
 * @author calvin
 */
@SuppressWarnings("serial")
@Results( { @Result(name = CRUDActionSupport.RELOAD, location = "user.action?page.pageRequest=${page.pageRequest}", type = "redirect") })
public class UserAction extends CRUDActionSupport<User> {

	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;

	// 基本属性
	private User entity;
	private Long id;
	private Page<User> page = new Page<User>(5);//每页5条记录

	// 角色相关属性
	private List<Role> allRoles; //全部可选角色列表
	private List<Long> checkedRoleIds; //页面中钩选的角色id列表

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

	public Page<User> getPage() {
		return page;
	}

	// CRUD Action 函数 //

	@Override
	public String list() throws Exception {
		page = userManager.getAll(page);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		allRoles = roleManager.getAll();
		checkedRoleIds = entity.getRoleIds();
		return INPUT;
	}

	@Override
	public String save() throws Exception {
		//根据页面上的checkbox 整合User的Roles Set
		HibernateWebUtils.mergeByCheckedIds(entity.getRoles(), checkedRoleIds, Role.class);

		userManager.save(entity);
		addActionMessage("保存用户成功");
		return RELOAD;
	}

	@Override
	public String delete() throws Exception {
		try {
			userManager.delete(id);
			addActionMessage("删除用户成功");
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			addActionMessage(e.getMessage());
		}
		return RELOAD;
	}

	// 其他属性访问函数与Action函数 //

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}

	/**
	 * 根据属性过滤条件搜索用户. 
	 */
	public String search() throws Exception {
		//因为搜索时不保存分页参数,因此将页面大小设到最大.
		page.setPageSize(Page.MAX_PAGESIZE);

		HttpServletRequest request = Struts2Utils.getRequest();
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		page = userManager.search(page, filters);
		return SUCCESS;
	}

	/**
	 * 支持使用Jquery.validate Ajax检验用户名是否重复.
	 */
	public String checkLoginName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String loginName = request.getParameter("loginName");
		String orgLoginName = request.getParameter("orgLoginName");

		if (userManager.isLoginNameUnique(loginName, orgLoginName)) {
			Struts2Utils.renderText("true");
		} else {
			Struts2Utils.renderText("false");
		}
		//因为直接输出内容而不经过jsp,因此返回null.
		return null;
	}
}
