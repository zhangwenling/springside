package org.springside.examples.miniservice.functional.rs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;

import org.dozer.DozerBeanMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.functional.BaseFunctionalTestCase;
import org.springside.examples.miniservice.rs.client.UserResourceClient;
import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.UniformInterfaceException;

public class UserResourceServiceTest extends BaseFunctionalTestCase {

	private static UserResourceClient client;

	@BeforeClass
	public static void setUpClient() throws Exception {
		client = new UserResourceClient();
		client.setBaseUrl(BASE_URL + "/rs");
	}

	@Test
	public void createUser() {
		User user = AccountData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);

		URI uri = client.createUser(dto);
		assertNotNull(uri);
		System.out.println("Created user uri:" + uri);
	}

	@Test
	public void createUserWithInvalidLoginName() {

		User user = AccountData.getRandomUser();
		UserDTO dto = new DozerBeanMapper().map(user, UserDTO.class);
		dto.setLoginName(null);

		try {

			URI uri = client.createUser(dto);
			fail("Should thrown exception while invalid id");
		} catch (UniformInterfaceException e) {
			assertEquals(400, e.getResponse().getStatus());
		}
	}
}
