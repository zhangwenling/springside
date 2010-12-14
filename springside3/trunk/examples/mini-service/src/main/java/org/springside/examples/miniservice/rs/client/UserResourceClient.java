package org.springside.examples.miniservice.rs.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * 使用Jersey Client的User REST客户端.
 * 
 * @author calvin
 */
public class UserResourceClient {

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		client = JerseyClientUtils.createClient(baseUrl);
	}

	public URI createUser(UserDTO user) {
		ClientResponse response = client.path("/users").entity(user, MediaType.APPLICATION_JSON)
				.post(ClientResponse.class);
		if (201 == response.getStatus()) {
			return response.getLocation();
		} else {
			throw new UniformInterfaceException(response);
		}
	}

	public boolean authUser(String loginName, String password) {
		String result = client.path("/users/auth").queryParam("loginName", loginName).queryParam("password", password)
				.get(String.class);
		return new Boolean(result);
	}
}
