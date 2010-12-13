package org.springside.examples.miniservice.rs.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResourceUtils {

	public static final String CHARSET = ";charset=UTF-8";

	/**
	 * 创建WebApplicationException, 使用标准状态码与自定义信息.
	 */
	public static WebApplicationException buildException(Status status, String message) {
		return new WebApplicationException(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

	/**
	 * 创建WebApplicationException, 使用自定义状态码与自定义信息.
	 */
	public static WebApplicationException buildException(int status, String message) {
		return new WebApplicationException(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
