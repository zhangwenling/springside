package org.springside.examples.miniservice.dao.account;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;

@Component
public class AccountMyBatisDao extends SqlSessionDaoSupport {

	public List<Department> getAllDepartment() {
		return (List<Department>) getSqlSession().selectList("Account.getAllDepartment");
	}

	public Department getDepartmentDetail(Long id) {
		return (Department) getSqlSession().selectOne("Account.getDepartmentDetail", id);
	}

	public void saveUser(User user) {
	}

	public Long countByLoginNamePassword(String loginName, String password) {
		return 0L;
	}

}
