package org.springside.examples.miniservice.functional.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.functional.BaseFunctionalTestCase;
import org.springside.examples.miniservice.ws.UserWebService;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.AuthUserResult;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetDepartmentListResult;
import org.springside.examples.miniservice.ws.result.WSResult;

/**
 * UserService Web服务的功能测试, 测试主要的接口调用.
 * 
 * 一般使用在Spring applicaitonContext.xml中用<jaxws:client/>创建的Client, 也可以用JAXWS的API自行创建.
 * 
 * @author calvin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-ws-client.xml" })
public class UserWebServiceTest extends BaseFunctionalTestCase {

	@Autowired
	private UserWebService userWebService;

	/**
	 * 测试认证用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void authUser() {
		AuthUserResult result = userWebService.authUser("admin", "admin");
		assertEquals(true, result.isValid());
	}

	/**
	 * 测试创建用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void createUser() {
		User user = AccountData.getRandomUser();

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(user.getLoginName());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());

		CreateUserResult result = userWebService.createUser(userDTO);

		assertEquals(WSResult.SUCCESS, result.getCode());
		assertNotNull(result.getUserId());
	}

	/**
	 * 测试创建用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void createUserWithInvalidLoginName() {
		User user = AccountData.getRandomUser();
		UserDTO userDTO = new DozerBeanMapper().map(user, UserDTO.class);

		userDTO.setLoginName(null);
		CreateUserResult result = userWebService.createUser(userDTO);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());

		userDTO.setLoginName("user2");
		result = userWebService.createUser(userDTO);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
	}

	/**
	 * 测试获取全部部门,使用CXF的API自行动态创建Client.
	 */
	@Test
	public void getDepartmentList() {
		String address = BASE_URL + "/ws/userservice";

		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setAddress(address);
		proxyFactory.setServiceClass(UserWebService.class);
		UserWebService userWebServiceCreated = (UserWebService) proxyFactory.create();

		//(可选)重新设定endpoint address.
		((BindingProvider) userWebServiceCreated).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);

		GetDepartmentListResult result = userWebServiceCreated.getDepartmentList();

		assertEquals(2, result.getDepartmentList().size());
		assertEquals("Development", result.getDepartmentList().get(0).getName());
	}
}
