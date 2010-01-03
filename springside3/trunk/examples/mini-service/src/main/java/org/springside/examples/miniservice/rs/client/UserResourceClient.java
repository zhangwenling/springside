package org.springside.examples.miniservice.rs.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springside.examples.miniservice.rs.dto.JAXBContextResolver;
import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的User REST客户端.
 * 
 * @author calvin
 */
public class UserResourceClient {
	private WebResource client;

	public UserResourceClient(String baseUrl) {
		ClientConfig config = new DefaultClientConfig();
		config.getClasses().add(JAXBContextResolver.class);
		Client jerseyClient = Client.create(config);
		client = jerseyClient.resource(baseUrl);
	}

	public List<UserDTO> getAllUser() {
		return client.path("/users").accept(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
		});
	}

	public UserDTO getUser(Long id) {
		return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	}

	public String createUser(UserDTO user) {
		String id = client.path("/users").type("application/json").accept(MediaType.TEXT_PLAIN)
				.post(String.class, user);
		return id;
	}
}
