package org.springside.examples.miniweb.unit.service.security;

import static org.junit.Assert.*;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springside.examples.miniweb.dao.security.UserDao;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.service.security.UserDetailServiceImpl;
import org.springside.modules.utils.ReflectionUtils;

/**
 * UserDettailServiceImpl的单元测试用例. 
 * 
 * 使用EasyMock对UserManager进行模拟.
 * 
 * @author calvin
 */
public class UserDetailServiceImplTest {
	private UserDetailServiceImpl	userDetailService	= new UserDetailServiceImpl();
	private UserDao					userDao				= null;

	@Before
	public void setUp() {
		//创建mock对象
		userDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(userDetailService, "userDao", userDao);
	}

	@After
	public void tearDown() {
		//确认的脚本都已执行
		EasyMock.verify(userDao);
	}

	@Test
	public void loadUserExist() {
		//准备数据
		String loginName = "user";
		String passwd = "userPass";
		String authName = "A_aaa";

		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(passwd);
		Role role = new Role();
		user.getRoles().add(role);
		Authority auth = new Authority();
		auth.setName(authName);
		role.getAuthorities().add(auth);

		//录制脚本
		EasyMock.expect(userDao.findByUnique("loginName", loginName)).andReturn(user);
		EasyMock.replay(userDao);

		//执行测试
		UserDetails userDetails = userDetailService.loadUserByUsername(loginName);

		//校验结果
		assertEquals(loginName, userDetails.getUsername());
		assertEquals(passwd, userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().length);
		assertEquals(new GrantedAuthorityImpl(authName), userDetails.getAuthorities()[0]);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserNotExist() {
		//录制脚本
		EasyMock.expect(userDao.findByUnique("loginName", "userNameNotExist")).andReturn(null);
		EasyMock.replay(userDao);
		//执行测试
		userDetailService.loadUserByUsername("userNameNotExist");
	}
}