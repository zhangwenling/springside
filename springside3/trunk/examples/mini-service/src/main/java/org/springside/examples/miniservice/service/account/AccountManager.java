package org.springside.examples.miniservice.service.account;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniservice.dao.account.AccountMyBatisDao;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;

/**
 * 帐号管理类.
 * 
 * 实现领域对象用户及其相关实体的所有业务管理函数.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Component
//默认将类中的所有函数纳入事务管理.
@Transactional
public class AccountManager {

	private AccountMyBatisDao accountDao = null;

	@Transactional(readOnly = true)
	public List<Department> getAllDepartment() {
		return accountDao.getAllDepartment();
	}

	@Transactional(readOnly = true)
	public Department getDepartmentDetail(Long id) {
		return  accountDao.getDepartmentDetail(id);
	}

	public void saveUser(User user) {
		accountDao.saveUser(user);
	}

	/**
	 * 验证用户名密码. 
	 * 
	 * @return 验证通过时返回true,用户名或密码错误时返回false.
	 */
	@Transactional(readOnly = true)
	public boolean authenticate(String loginName, String password) {
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			return false;
		}

		return (accountDao.countByLoginNamePassword(loginName, password) == 1);
	}

	@Autowired
	public void setAccountDao(AccountMyBatisDao accountDao) {
		this.accountDao = accountDao;
	}
}
