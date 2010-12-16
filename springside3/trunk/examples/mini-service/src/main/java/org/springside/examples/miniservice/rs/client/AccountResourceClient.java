package org.springside.examples.miniservice.rs.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.miniservice.rs.dto.DepartmentDTO;
import org.springside.examples.miniservice.rs.dto.UserDTO;
import org.springside.examples.miniservice.utils.JerseyClientUtils;

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

	/**
	 * 获取部门.
	 */
	public DepartmentDTO getDepartmentDetail(Long id) {
		return client.path("/departments/" + id).accept(MediaType.APPLICATION_JSON).get(DepartmentDTO.class);
	}

	/**
	 * 获取用户.
	 */
	public UserDTO getUser(Long id) {
		return client.path("/users/" + id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	}

	/**
	 * 查询用户列表, 使用URL查询参数发送查询条件, 返回用户列表.
	 */
	public List<UserDTO> searchUser(String loginName, String name) {
		WebResource wr = client.path("/users/search");
		if (StringUtils.isNotBlank(loginName)) {
			wr = wr.queryParam("loginName", loginName);
		}
		if (StringUtils.isNotBlank(name)) {
			wr = wr.queryParam("name", name);
		}
		return wr.accept(MediaType.APPLICATION_JSON).get(JerseyClientUtils.listType(UserDTO.class));
	}

	/**
	 * 创建用户, 使用Post发送JSON编码的用户对象, 返回代表用户的url.
	 */
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
