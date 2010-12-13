package org.springside.examples.miniservice.dao.account;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.User;

@Component
public class UserMyBatisDao extends SqlSessionDaoSupport {

	public List<User> getAll() {
		return (List<User>) getSqlSession().selectList("User.getAll");
	}

	public User get(Long id) {
		return (User) getSqlSession().selectOne("User.get", id);
	}

	public void save(User user) {
	}

	public Long countByLoginNamePassword(String loginName, String password) {
		return 0L;
	}
}
