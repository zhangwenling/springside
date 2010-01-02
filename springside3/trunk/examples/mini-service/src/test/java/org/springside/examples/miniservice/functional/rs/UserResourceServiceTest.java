package org.springside.examples.miniservice.functional.rs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JSONProvider;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.data.UserData;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.rs.dto.UserDTO;

public class UserResourceServiceTest extends Assert {

	private WebClient client;

	@Before
	public void setUp() {
		List providerList = new ArrayList();
		JSONProvider jsonProvider = new JSONProvider();
		//jsonProvider.setSupportUnwrapped(true);
		//jsonProvider.setDropRootElement(true);
		providerList.add(jsonProvider);

		client = WebClient.create("http://localhost:8080/mini-service/services/rs", providerList);
	}

	@Test
	public void getUser() {
		UserDTO user = client.path("/users/1").accept("application/json").get(UserDTO.class);
		assertEquals("admin", user.getLoginName());
		assertEquals(2, user.getRoleList().size());
		assertEquals("管理员", user.getRoleList().get(0).getName());
	}

	@Test
	public void getUserWithInvalidId() {
		try {
			client.path("/users/999").accept("application/json").get(UserDTO.class);
			fail("Should thrown exception while invalid id");
		} catch (WebApplicationException e) {
			assertEquals(HttpServletResponse.SC_NOT_FOUND, e.getResponse().getStatus());
		}
	}

	@Test
	public void getAllUser() {
		Collection<? extends UserDTO> userList = client.path("/users").accept("application/xml").getCollection(
				UserDTO.class);

		assertTrue(userList.size() >= 6);
		UserDTO admin = userList.iterator().next();
		assertEquals("admin", admin.getLoginName());
	}

	@Test
	public void createUser() {
		User user = UserData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);
		Long id = client.path("/users").type("application/json").post(dto, Long.class);
		System.out.println("Created user id:" + id);
	}

}
