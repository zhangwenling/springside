package org.springside.examples.showcase.rs.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.showcase.rs.dto.UserDTO;
import org.springside.modules.utils.encode.JsonBinder;
import org.springside.modules.utils.web.ServletUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的User REST客户端.
 * 在Mini-Service演示的基础上添加更多演示.
 * 
 * @author calvin
 */
public class UserResourceClient {

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	/**
	 * 访问有SpringSecurity安全控制的页面, 进行HttpBasic的登录.
	 */
	public List<UserDTO> getAllUser() {
		String authentication = ServletUtils.encodeHttpBasic("admin", "admin");
		return client.path("/users").header(ServletUtils.AUTHENTICATION_HEADER, authentication)
				.accept(MediaType.APPLICATION_JSON).get(new GenericType<List<UserDTO>>() {
				});
	}

	public UserDTO getUser(Long id) {
		return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	}

	/**
	 * 返回html格式的特定内容.
	 */
	public String searchUserHtml(String name) {
		return client.path("/users/search").queryParam("name", name).queryParam("format", "html").get(String.class);
	}

	/**
	 * 无公共DTO类定义, 取得返回JSON字符串后自行转换DTO.
	 */
	public UserDTO searchUserJson(String name) {
		String jsonString = client.path("/users/search").queryParam("name", name).get(String.class);
		return JsonBinder.buildNormalBinder().fromJson(jsonString, UserDTO.class);
	}
}
