package org.springside.examples.miniservice.service.account;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniservice.dao.account.AccountDao;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.utils.AssertUtils;
import org.springside.modules.utils.validator.ValidatorHolder;

import com.google.common.collect.Maps;

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

	private AccountDao accountDao = null;

	@Transactional(readOnly = true)
	public Department getDepartmentDetail(Long id) {
		AssertUtils.notNull(id, "id参数为空");
		return accountDao.getDepartmentDetail(id);
	}

	@Transactional(readOnly = true)
	public User getUser(Long id) {
		AssertUtils.notNull(id, "id参数为空");
		return accountDao.getUser(id);
	}

	@Transactional(readOnly = true)
	public List<User> searchUser(String loginName, String name) {
		Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("loginName", loginName);
		parameters.put("name", name);
		return accountDao.searchUser(parameters);
	}

	public Long saveUser(User user) throws ConstraintViolationException {
		AssertUtils.notNull(user, "用户参数为空");
		//使用Hibernate Validator校验请求参数
		ValidatorHolder.validateWithException(user);

		return accountDao.saveUser(user);
	}

	@Autowired
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
}
