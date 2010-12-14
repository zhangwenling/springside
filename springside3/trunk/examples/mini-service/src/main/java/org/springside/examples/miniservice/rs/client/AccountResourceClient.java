package org.springside.examples.miniservice.rs.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.miniservice.rs.dto.DepartmentDTO;
import org.springside.examples.miniservice.rs.dto.UserDTO;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * 使用Jersey Client的Account Service REST客户端.
 * 
 * @author calvin
 */
public class AccountResourceClient {

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		client = JerseyClientUtils.createClient(baseUrl);
	}

	public DepartmentDTO getDepartmentDetail(Long id) {
		return client.path("/departments/" + id).accept(MediaType.APPLICATION_JSON).get(DepartmentDTO.class);
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
}
