package org.springside.examples.miniservice.functional.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniservice.data.UserData;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.ws.api.Constants;
import org.springside.examples.miniservice.ws.api.UserWebService;
import org.springside.examples.miniservice.ws.api.dto.RoleDTO;
import org.springside.examples.miniservice.ws.api.dto.UserDTO;
import org.springside.examples.miniservice.ws.api.result.CreateUserResult;
import org.springside.examples.miniservice.ws.api.result.GetAllUserResult;
import org.springside.examples.miniservice.ws.api.result.WSResult;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * UserService Web服务的功能测试, 测试主要的接口调用.
 * 
 * 一般使用在Spring中用<jaxws:client/>创建的Client, 也可以用JAXWS的API自行创建.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-cxf-client.xml" })
public class UserWebServiceTest extends SpringContextTestCase {

	/**
	 * 测试认证用户,在Spring中用<jaxws:client/>创建的Client.
	 */
	@Test
	public void authUser() {
		UserWebService userWebService = (UserWebService) applicationContext.getBean("userWebService");
		WSResult result = userWebService.authUser("admin", "admin");
		assertEquals(WSResult.SUCCESS, result.getCode());
	}

	/**
	 * 测试创建用户,在Spring中用<jaxws:client/>创建的Client.
	 */
	@Test
	public void createUser() {
		UserDTO userDTO = new UserDTO();
		User user = UserData.getRandomUser();
		userDTO.setLoginName(user.getLoginName());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());

		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(1L);
		userDTO.getRoles().add(roleDTO);

		UserWebService userWebService = (UserWebService) applicationContext.getBean("userWebService");
		CreateUserResult result = userWebService.createUser(userDTO);
		assertEquals(WSResult.SUCCESS, result.getCode());
		assertNotNull(result.getUserId());
	}

	/**
	 * 测试获取全部用户,使用JAXWS的API自行创建Client.
	 */
	@Test
	public void getAllUser() throws MalformedURLException {
		URL wsdlURL = new URL("http://localhost:8080/mini-service/services/UserService?wsdl");
		QName UserServiceName = new QName(Constants.NS, "UserService");
		Service service = Service.create(wsdlURL, UserServiceName);
		UserWebService userWebService = service.getPort(UserWebService.class);

		GetAllUserResult result = userWebService.getAllUser();
		assertTrue(result.getUserList().size() > 0);
	}
}
