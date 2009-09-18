#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.ws.user;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ${package}.data.UserData;
import ${package}.entity.user.User;
import ${package}.service.user.UserManager;
import ${package}.ws.api.dto.UserDTO;
import ${package}.ws.api.result.GetAllUserResult;
import ${package}.ws.api.result.WSResult;
import ${package}.ws.impl.UserWebServiceImpl;
import org.springside.modules.utils.ReflectionUtils;

/**
 * User Web Service的单元测试用例,测试WebService操作的返回码.
 * 
 * 使用EasyMock对UserManager进行模拟.
 * 
 * @author calvin
 */
public class UserWebServiceTest extends Assert {
	private UserWebServiceImpl userWebService = new UserWebServiceImpl();
	private UserManager userManager = null;

	@Before
	public void setUp() {
		//创建mock对象
		userManager = EasyMock.createNiceMock(UserManager.class);
		ReflectionUtils.setFieldValue(userWebService, "userManager", userManager);
		ReflectionUtils.setFieldValue(userWebService, "dozer", new DozerBeanMapper());
	}

	@After
	public void tearDown() {
		//确认的脚本都已执行
		EasyMock.verify(userManager);
	}

	/**
	 * 测试dozer正确映射.
	 */
	public void dozerBinding() {
		User user = UserData.getRandomUserWithAdminRole();
		List<User> list = new ArrayList<User>();
		list.add(user);
		EasyMock.expect(userManager.getAllUser()).andReturn(list);
		EasyMock.replay(userManager);

		GetAllUserResult result = userWebService.getAllUser();
		assertEquals(WSResult.SUCCESS, result.getCode());
		UserDTO dto = result.getUserList().get(0);
		assertEquals(user.getLoginName(), dto.getLoginName());
		assertEquals(user.getRoleList().get(0).getName(), dto.getRoleList().get(0).getName());
	}

	/**
	 * 测试参数错误时的返回码.
	 */
	@Test
	public void validateParamter() {
		EasyMock.replay(userManager);
		WSResult result = userWebService.createUser(null);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
	}

	/**
	 * 测试系统内部抛出异常时的处理.
	 */
	@Test
	public void handleException(){
		EasyMock.expect(userManager.getAllUser()).andStubThrow(new RuntimeException("oh.."));
		EasyMock.replay(userManager);

		GetAllUserResult result = userWebService.getAllUser();
		assertEquals(WSResult.SYSTEM_ERROR, result.getCode());
		assertEquals(WSResult.SYSTEM_ERROR_MESSAGE, result.getMessage());
	}

	/**
	 * 用户认证测试.
	 * 分别测试正确用户名与正确,错误密码,无密码三种情况的返回码.
	 */
	@Test
	public void authUser() {
		//准备数据,录制脚本
		EasyMock.expect(userManager.authenticate("admin", "admin")).andReturn(true);
		EasyMock.expect(userManager.authenticate("admin", "errorPasswd")).andReturn(false);
		EasyMock.replay(userManager);

		//执行输入正确的测试
		WSResult result = userWebService.authUser("admin", "admin");
		assertEquals(WSResult.SUCCESS, result.getCode());

		//执行输入错误的测试
		result = userWebService.authUser("admin", "errorPasswd");
		assertEquals(WSResult.AUTH_ERROR, result.getCode());

		result = userWebService.authUser("admin", "");
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
	}
}
