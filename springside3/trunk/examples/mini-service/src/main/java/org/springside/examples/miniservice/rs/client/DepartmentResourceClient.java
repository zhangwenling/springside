package org.springside.examples.miniservice.rs.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.miniservice.rs.dto.DepartmentDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * 使用Jersey Client的User REST客户端.
 * 
 * @author calvin
 */
public class DepartmentResourceClient {

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	public List<DepartmentDTO> getAllDepartment() {
		return client.path("/departments").accept(MediaType.APPLICATION_JSON).get(new GenericType<List<DepartmentDTO>>() {
		});
	}

	public DepartmentDTO getDepartmentDetail(Long id) {
		return client.path("/departments/" + id).accept(MediaType.APPLICATION_JSON).get(DepartmentDTO.class);
	}
}
