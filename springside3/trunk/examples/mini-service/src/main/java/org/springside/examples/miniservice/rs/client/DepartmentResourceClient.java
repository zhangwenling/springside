package org.springside.examples.miniservice.rs.client;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Required;
import org.springside.examples.miniservice.rs.dto.DepartmentDTO;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

/**
 * 使用Jersey Client的User REST客户端.
 * 
 * @author calvin
 */
public class DepartmentResourceClient {

	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		client = JerseyClientUtils.createClient(baseUrl);
	}

	public List<DepartmentDTO> getDepartmentList() {
		return client.path("/departments").accept(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<DepartmentDTO>>() {
				});
	}

	public DepartmentDTO getDepartmentDetail(Long id) {
		return client.path("/departments/" + id).accept(MediaType.APPLICATION_JSON).get(DepartmentDTO.class);
	}
}
