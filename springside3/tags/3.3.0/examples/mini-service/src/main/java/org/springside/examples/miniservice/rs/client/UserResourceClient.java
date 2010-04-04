package org.springside.examples.miniservice.rs.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的User REST客户端.
 * 
 * @author calvin
 */
public class UserResourceClient {

	private WebResource client;

	public UserResourceClient(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	public List<UserDTO> getAllUser() {
		return client.path("/users").accept(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
		});
	}

	public UserDTO getUser(Long id) {
		return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	}

	public URI createUser(UserDTO user) {
		ClientResponse response = client.path("/users").type("application/json").post(ClientResponse.class, user);
		return response.getLocation();
	}
}
