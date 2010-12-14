package org.springside.examples.miniservice.dao.account;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;

@Component
public class AccountMyBatisDao extends SqlSessionDaoSupport {

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
	public List<User> searchUser(Map<String, String> parameters) {
		return getSqlSession().selectList("Account.searchUser", parameters);
	}
}
