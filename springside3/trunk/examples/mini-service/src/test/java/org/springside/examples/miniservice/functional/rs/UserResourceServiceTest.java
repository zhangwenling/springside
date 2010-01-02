package org.springside.examples.miniservice.functional.rs;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.data.UserData;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.rs.dto.UserDTO;

public class UserResourceServiceTest extends Assert {
	
	private WebClient client ;
	
	@Before
	public void setUp() {
		client = WebClient.create("http://localhost:8080/mini-service/services/rs");
	}

	/*	@Test
		public void getAllUser() {
			List<UserDTO> userList = client.path("/users").accept("application/json").get(List.class);
			assertTrue(userList.size()>2);
			assertEquals("admin", userList.get(0).getLoginName());
		}*/

	@Test
	public void getUser() {
		UserDTO user = client.path("/users/1").accept("application/json").get(UserDTO.class);
		assertEquals("admin", user.getLoginName());
	}

	@Test
	public void getUserWithInvalidId() {
		try {
			UserDTO user = client.path("/users/999").accept("application/json").get(UserDTO.class);
		} catch (WebApplicationException e) {
			assertEquals(HttpServletResponse.SC_NOT_FOUND, e.getResponse().getStatus());
		}
	}

	@Test
	public void createUser() {
		User user = UserData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);
		Response response = client.path("/users").type("application/json").post(dto);
		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
	}

}
