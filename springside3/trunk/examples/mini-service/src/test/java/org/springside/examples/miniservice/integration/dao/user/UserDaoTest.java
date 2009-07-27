package org.springside.examples.miniservice.integration.dao.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.dao.UserDao;
import org.springside.examples.miniservice.entity.user.Role;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.unit.service.user.UserData;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * UserManager的集成测试用例.
 * 
 * 调用实际的DAO类进行操作,默认在操作后进行回滚.
 * 
 * @author calvin
 */
public class UserDaoTest extends SpringTxTestCase {

	@Autowired
	private UserDao entityDao;

	@Test
	//如果你需要真正插入数据库,将Rollback设为false
	//@Rollback(false) 
	public void crudEntity() {
		//新建并保存用户.
		User entity = UserData.getRandomUser();
		entityDao.save(entity);
		//强制执行sql语句
		flush();

		//获取用户
		entity = entityDao.get(entity.getId());
		
		//修改用户
		entity.setEmail("new.email@springside.org.cn");
		entityDao.save(entity);
		flush();
		
		//删除用户
		entityDao.delete(entity.getId());
		flush();
	}

	@Test
	public void crudEntityWithRole() {
		//保存带角色的用户
		User user = UserData.getRandomUser();
		Role role = UserData.getRandomRole();
		user.getRoles().add(role);

		entityDao.save(user);
		flush();

		user = entityDao.get(user.getId());
		assertEquals(1, user.getRoles().size());

		//删除用户的角色
		user.getRoles().remove(role);
		flush();
		user = entityDao.get(user.getId());
		assertEquals(0, user.getRoles().size());
	}

	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void savenNotUniqueUser() {
		User user = UserData.getRandomUser();
		user.setLoginName("admin");

		entityDao.save(user);
		flush();
	}
}
