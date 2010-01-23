package org.springside.examples.miniweb.unit.service.security;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springside.examples.miniweb.data.SecurityEntityData;
import org.springside.examples.miniweb.entity.security.Authority;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.examples.miniweb.entity.security.User;
import org.springside.examples.miniweb.service.security.SecurityEntityManager;
import org.springside.examples.miniweb.service.security.UserDetailsServiceImpl;
import org.springside.modules.utils.ReflectionUtils;

/**
 * UserDetailsServiceImpl的测试用例, 测试Service层的业务逻辑. 
 * 
 * 使用EasyMock对UserManager进行模拟.
 * 
 * @author calvin
 */
public class UserDetailsServiceImplTest extends Assert {

	private UserDetailsServiceImpl userDetailService;
	private SecurityEntityManager mockSecurityEntityManager;

	@Before
	public void setUp() {
		userDetailService = new UserDetailsServiceImpl();
		mockSecurityEntityManager = EasyMock.createMock(SecurityEntityManager.class);
		ReflectionUtils.setFieldValue(userDetailService, "securityEntityManager", mockSecurityEntityManager);
	}

	@After
	public void tearDown() {
		EasyMock.verify(mockSecurityEntityManager);
	}

	@Test
	public void loadUserExist() {

		String authName = "foo";
		//准备数据
		User user = SecurityEntityData.getRandomUser();
		Role role = SecurityEntityData.getRandomRole();
		user.getRoleList().add(role);
		Authority auth = new Authority();
		auth.setName(authName);
		role.getAuthorityList().add(auth);

		//录制脚本
		EasyMock.expect(mockSecurityEntityManager.findUserByLoginName(user.getLoginName())).andReturn(user);
		EasyMock.replay(mockSecurityEntityManager);

		//执行测试
		UserDetails userDetails = userDetailService.loadUserByUsername(user.getLoginName());

		//校验结果
		assertEquals(user.getLoginName(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().size());
		assertEquals(new GrantedAuthorityImpl(auth.getPrefixedName()),
				userDetails.getAuthorities().iterator().next());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserNotExist() {
		//录制脚本
		EasyMock.expect(mockSecurityEntityManager.findUserByLoginName("userNameNotExist")).andReturn(null);
		EasyMock.replay(mockSecurityEntityManager);
		//执行测试
		userDetailService.loadUserByUsername("userNameNotExist");
	}
}