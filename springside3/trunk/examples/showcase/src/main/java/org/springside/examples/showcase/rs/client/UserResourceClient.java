package org.springside.examples.showcase.rs.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springside.examples.showcase.rs.dto.JAXBContextResolver;
import org.springside.examples.showcase.rs.dto.UserDTO;
import org.springside.modules.web.ServletUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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

	/**
	 * 访问有SpringSecurity安全控制的页面, 进行HttpBasic的登录.
	 */
	public List<UserDTO> getAllUser() {
		String authentication = ServletUtils.encodeHttpBasic("admin", "admin");
		return client.path("/users").header(ServletUtils.AUTHENTICATION_HEADER, authentication).accept(
				MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
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
