package org.springside.examples.miniservice.rs.server;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

public class JerseyServerUtils {

	/**
	 * 创建WebApplicationException并记录日志, 使用标准状态码与自定义信息并记录错误信息.
	 */
	public static WebApplicationException buildException(Logger logger, Status status, String message) {
		logger.error(status.getStatusCode() + ":" + message);
		return new WebApplicationException(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

	/**
	 * 创建WebApplicationException并记录日志, 使用自定义状态码与自定义信息.
	 */
	public static WebApplicationException buildException(Logger logger, int status, String message) {
		logger.error(status + ":" + message);
		return new WebApplicationException(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

	/**
	 * 创建WebApplicatonExcetpion并记录日志, 使用状态码为500与RuntimeExcetpion中信息.
	 * 如RuntimeException为WebApplicatonExcetpion则跳过不进行处理.
	 */
	public static WebApplicationException buildException(Logger logger, RuntimeException e) {
		if (e instanceof WebApplicationException) {
			return (WebApplicationException) e;
		} else {
			logger.error("500:" + e.getMessage(), e);
			return new WebApplicationException();
		}
	}

}
