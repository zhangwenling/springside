package org.springside.examples.miniservice.functional.rs;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.data.UserData;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.rs.client.UserResourceClient;
import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.UniformInterfaceException;

public class UserResourceServiceTest extends Assert {

	private UserResourceClient client;

	@Before
	public void setUp() {
		client = new UserResourceClient("http://localhost:8080/mini-service/rs");
	}

	@Test
	public void getAllUser() {
		List<UserDTO> userList = client.getAllUser();
		assertTrue(userList.size() >= 6);
		UserDTO admin = userList.iterator().next();
		assertEquals("admin", admin.getLoginName());
	}

	@Test
	public void getUser() {
		UserDTO user = client.getUser(1L);
		assertEquals("admin", user.getLoginName());
		assertEquals(2, user.getRoleList().size());
		assertEquals("管理员", user.getRoleList().get(0).getName());
	}

	@Test
	public void getUserWithInvalidId() {
		try {
			client.getUser(999L);
			fail("Should thrown exception while invalid id");
		} catch (UniformInterfaceException e) {
			assertEquals(HttpServletResponse.SC_NOT_FOUND, e.getResponse().getStatus());
		}
	}

	@Test
	public void createUser() {
		User user = UserData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);

		String id = client.createUser(dto);
		System.out.println("Created user id:" + id);
	}

}
