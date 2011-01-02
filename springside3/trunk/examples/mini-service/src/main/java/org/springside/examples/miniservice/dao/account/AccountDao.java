package org.springside.examples.miniservice.dao.account;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.mybatis.MyBatisDao;

@Component
public class AccountDao extends MyBatisDao {

	public Department getDepartmentDetail(Long id) {
		return (Department) getSqlSession().selectOne("Account.getDepartmentDetail", id);
	}

	public Long saveUser(User user) {
		getSqlSession().insert("Account.saveUser", user);
		return user.getId();
	}

	public User getUser(Long id) {
		return (User) getSqlSession().selectOne("Account.getUser", id);
	}

	@SuppressWarnings("unchecked")
	public Page<User> searchUser(Page page, Map<String, Object> parameters) {
		return selectPage(page, "Account.searchUser", parameters);
	}

}
