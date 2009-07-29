package org.springside.examples.miniweb.service.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.dao.security.AuthorityDao;
import org.springside.examples.miniweb.dao.security.ResourceDao;
import org.springside.examples.miniweb.dao.security.RoleDao;
import org.springside.examples.miniweb.dao.security.UserDao;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.service.ServiceException;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 用户及相关的角色,授权管理类.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager {

	protected Logger logger = LoggerFactory.getLogger(UserManager.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private ResourceDao resourceDao;

	// User Manager //
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		userDao.save(entity);
	}

	public void deleteUser(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.find(page, filters);
	}
	
	@Transactional(readOnly = true)
	public User findUerByLoginName(String loginName) {
		return userDao.findByUnique("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 *
	 * @return loginName在数据库中唯一或等于orgLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String loginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", loginName, oldLoginName);
	}

	// Role Manager //
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}
	
	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll();
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	public void deleteRole(Long id) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户角色", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员角色");
		}

		roleDao.delete(id);
	}

	// Resource Manager //
	public List<Resource> getUrlResourceWithAuthorities() {
		return resourceDao.getUrlResourceWithAuthorities();
	}
	
	// Authority Manager //
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}
}
