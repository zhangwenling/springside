package org.springside.examples.miniservice.functional.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springside.examples.miniservice.data.UserData;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.ws.WSResult;
import org.springside.examples.miniservice.ws.user.UserWebService;
import org.springside.examples.miniservice.ws.user.dto.CreateUserResult;
import org.springside.examples.miniservice.ws.user.dto.GetAllUserResult;
import org.springside.examples.miniservice.ws.user.dto.RoleDTO;
import org.springside.examples.miniservice.ws.user.dto.UserDTO;

/**
 * UserService Web服务的功能测试, 测试主要的接口调用.
 * 
 * @author calvin
 */
public class UserWebServiceTest extends Assert {

	private static UserWebService userWebService;

	@BeforeClass
	public static void setUpBeforeClass() throws MalformedURLException {
		URL wsdlURL = new URL("http://localhost:8080/mini-service/services/UserService?wsdl");
		QName UserServiceName = new QName("http://miniservice.examples.springside.org", "UserService");
		Service service = Service.create(wsdlURL, UserServiceName);
		userWebService = service.getPort(UserWebService.class);
	}

	@Test
	public void getAllUser() {
		GetAllUserResult result = userWebService.getAllUser();
		assertEquals(6, result.getUserList().size());
	}

	@Test
	public void authUser() {
		WSResult result = userWebService.authUser("admin", "admin");
		assertEquals(WSResult.SUCCESS, result.getCode());
	}

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

		CreateUserResult result = userWebService.createUser(userDTO);
		assertEquals(WSResult.SUCCESS, result.getCode());
		assertNotNull(result.getUserId());
	}
}
