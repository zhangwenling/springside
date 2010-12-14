package org.springside.examples.miniservice.rs.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

public class ResourceUtils {

	public static final String CHARSET = ";charset=UTF-8";

	/**
	 * 创建WebApplicationException, 使用标准状态码与自定义信息.
	 */
	public static WebApplicationException buildException(Logger logger, Status status, String message) {
		logger.error(message);
		return new WebApplicationException(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

	/**
	 * 创建WebApplicationException, 使用自定义状态码与自定义信息.
	 */
	public static WebApplicationException buildException(Logger logger, int status, String message) {
		logger.error(message);
		return new WebApplicationException(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

	public static WebApplicationException buildException(Logger logger, RuntimeException e) {
		if (e instanceof WebApplicationException) {
			return (WebApplicationException) e;
		} else {
			logger.error(e.getMessage(), e);
			return new WebApplicationException();
		}
	}

}
