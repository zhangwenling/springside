package org.springside.examples.miniservice.dao.account;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;

import com.google.common.collect.Maps;

@Component
public class AccountMyBatisDao extends SqlSessionDaoSupport {

	public List<Department> getDepartmentList() {
		return getSqlSession().selectList("Account.getDepartmentList");
	}

	public Department getDepartmentDetail(Long id) {
		return (Department) getSqlSession().selectOne("Account.getDepartmentDetail", id);
	}

	public Long saveUser(User user) {
		getSqlSession().insert("Account.saveUser", user);
		return user.getId();
	}

	public int countByLoginNamePassword(String loginName, String password) {
		Map<String, String> parameters = Maps.newHashMap();
		parameters.put("loginName", loginName);
		parameters.put("password", password);
		return (Integer) getSqlSession().selectOne("Account.countByLoginNamePasswd", parameters);
	}
}
