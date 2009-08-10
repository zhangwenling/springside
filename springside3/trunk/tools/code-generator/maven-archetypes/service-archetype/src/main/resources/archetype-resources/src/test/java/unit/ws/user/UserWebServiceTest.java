#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.ws.user;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ${package}.service.user.UserManager;
import ${package}.ws.WSResult;
import ${package}.ws.user.UserWebServiceImpl;
import org.springside.modules.utils.ReflectionUtils;

/**
 * User Web服务的单元测试用例.
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
		userWebService.initDozer();
	}

	@After
	public void tearDown() {
		//确认的脚本都已执行
		EasyMock.verify(userManager);
	}

	/**
	 * 用户认证测试.
	 * 分别测试正确用户名与正确,错误密码两种情况.
	 */
	@Test
	public void authUser() {
		//准备数据,录制脚本
		EasyMock.expect(userManager.authenticate("admin", "admin")).andReturn(true);
		EasyMock.expect(userManager.authenticate("admin", "errorPasswd")).andReturn(false);
		EasyMock.replay(userManager);

		//执行输入正确的测试,校验结果
		WSResult result = userWebService.authUser("admin", "admin");
		assertEquals(WSResult.SUCCESS, result.getCode());

		//执行输入错误的测试,校验结果
		result = userWebService.authUser("admin", "errorPasswd");
		assertEquals(WSResult.AUTH_ERROR, result.getCode());
	}
}
