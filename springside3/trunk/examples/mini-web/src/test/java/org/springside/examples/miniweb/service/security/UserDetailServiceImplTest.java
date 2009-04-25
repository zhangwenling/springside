package org.springside.examples.miniweb.service.security;

import org.easymock.classextension.EasyMock;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.ExpectedException;
import org.springside.examples.miniweb.dao.security.UserDao;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.modules.test.junit38.SpringAnnotationTestCase;
import org.springside.modules.utils.ReflectionUtils;

/**
 * UserDettailServiceImpl的单元测试用例. 
 * 
 * 使用EasyMock对UserManager进行模拟.
 * 
 * @author calvin
 */
public class UserDetailServiceImplTest extends SpringAnnotationTestCase {
	private UserDetailServiceImpl userDetailService = new UserDetailServiceImpl();
	private UserDao userDao = null;

	@Override
	public void setUp() {
		//创建mock对象
		userDao = EasyMock.createMock(UserDao.class);
		ReflectionUtils.setFieldValue(userDetailService, "userDao", userDao);
	}

	@Override
	public void tearDown() {
		//确认的脚本都已执行
		EasyMock.verify(userDao);
	}

	public void testLoadUserExist() {
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

	@ExpectedException(value = UsernameNotFoundException.class)
	public void testLoadUserNotExist() {
		//录制脚本
		EasyMock.expect(userDao.findByUnique("loginName", "userNameNotExist")).andReturn(null);
		EasyMock.replay(userDao);
		//执行测试
		userDetailService.loadUserByUsername("userNameNotExist");
	}
}