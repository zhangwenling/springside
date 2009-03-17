package org.springside.examples.miniservice.ws.user;

import org.easymock.classextension.EasyMock;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.service.user.UserManager;
import org.springside.examples.miniservice.ws.WSResult;
import org.springside.modules.test.junit38.SpringAnnotationTestCase;
import org.springside.modules.utils.ReflectionUtils;

/**
 * User Web服务的单元测试用例.
 * 
 * 使用EasyMock对UserService进行模拟.
 * 
 * @author calvin
 * 
 */
public class UserWebServiceTest extends SpringAnnotationTestCase {
	private UserWebServiceImpl userWebService = new UserWebServiceImpl();

	private UserManager userManager = null;

	@Override
	public void setUp() {
		//创建mock对象
		userManager = EasyMock.createMock(UserManager.class);
		ReflectionUtils.setFieldValue(userWebService, "userManager", userManager);
		userWebService.initDozer();
	}

	@Override
	public void tearDown() {
		//确认的脚本都已执行
		EasyMock.verify(userManager);
	}

	public void testAuthUser() {
		//准备数据,录制脚本
		User user = new User();
		user.setId(1L);
		user.setLoginName("admin");

		org.easymock.EasyMock.expect(userManager.authenticate("admin", "admin")).andReturn(true);
		org.easymock.EasyMock.expect(userManager.authenticate("admin", "false")).andReturn(false);
		EasyMock.replay(userManager);

		//执行测试,校验结果
		WSResult result = userWebService.authUser("admin", "admin");
		assertEquals(result.getCode(), WSResult.SUCCESS);

		result = userWebService.authUser("admin", "false");
		assertEquals(result.getCode(), WSResult.FALSE);
	}
}
