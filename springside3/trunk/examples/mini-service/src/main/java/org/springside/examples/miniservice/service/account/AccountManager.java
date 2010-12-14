package org.springside.examples.miniservice.service.account;

import java.util.List;
import java.util.Map;

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
	public Department getDepartmentDetail(Long id) {
		return accountDao.getDepartmentDetail(id);
	}

	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return accountDao.getUser(id);
	}

	@Transactional(readOnly = true)
	public List<User> searchUser(Map<String, String> parameters) {
		return accountDao.searchUser(parameters);
	}

	public Long saveUser(User user) {
		return accountDao.saveUser(user);
	}

	@Autowired
	public void setAccountDao(AccountMyBatisDao accountDao) {
		this.accountDao = accountDao;
	}
}
