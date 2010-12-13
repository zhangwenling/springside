package org.springside.examples.miniservice.dao.account;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Role;
import org.springside.examples.miniservice.entity.account.User;

@Component
public class AccountMyBatisDao extends SqlSessionDaoSupport {

	public List<User> getAllUser() {
		return (List<User>) getSqlSession().selectList("User.getAllUser");
	}

	public User getUser(Long id) {
		return (User) getSqlSession().selectOne("Account.getUser", id);
	}

	public void saveUser(User user) {
	}
	
	public void updateUser(User user) {
	}

	public Long countByLoginNamePassword(String loginName, String password) {
		return 0L;
	}
	
	public List<Role> getAllRole(){
		return (List<Role>)getSqlSession().selectList("Account.getAllRole");
	}
}
