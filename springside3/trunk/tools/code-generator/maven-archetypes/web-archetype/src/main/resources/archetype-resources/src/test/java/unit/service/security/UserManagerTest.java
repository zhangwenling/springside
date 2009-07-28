#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.service.security;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ${package}.dao.security.UserDao;
import ${package}.service.ServiceException;
import ${package}.service.security.UserManager;
import org.springside.modules.utils.ReflectionUtils;

public class UserManagerTest extends Assert {

	private UserManager userManager = new UserManager();
	private UserDao userDao = null;
	
	@Before
	public void setUp() {
		//创建mock对象
		userDao = EasyMock.createNiceMock(UserDao.class);
		ReflectionUtils.setFieldValue(userManager, "userDao", userDao);
	}
	
	@After
	public void tearDown() {
		//确认脚本都已执行
		EasyMock.verify(userDao);
	}

	@Test
	public void testDetelteUser() {
		EasyMock.replay(userDao);
		userManager.deleteUser(2L);
	}

	@Test(expected = ServiceException.class)
	public void testDeleteAdmin(){
		EasyMock.replay(userDao);
		userManager.deleteRole(1L);
	}
}
