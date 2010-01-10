package org.springside.examples.showcase.functional.webservice.rs;

import java.net.URI;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.data.UserData;
import org.springside.examples.showcase.rs.client.UserResourceClient;
import org.springside.examples.showcase.rs.dto.UserDTO;

public class UserResourceServiceTest extends Assert {

	private UserResourceClient client;

	@Before
	public void setUp() {
		client = new UserResourceClient("http://localhost:8080/showcase/rs");
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
		assertEquals("Admin", user.getRoleList().get(0).getName());
	}

	@Test
	public void createUser() {
		User user = UserData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);

		URI uri = client.createUser(dto);
		System.out.println("Created user uri:" + uri);
	}

}
