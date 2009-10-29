package org.springside.examples.showcase.ws.server.api;

import javax.jws.WebService;

import org.springside.examples.showcase.ws.server.api.result.ImageResult;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * @author calvin
 */
@WebService(name = "ImageWebService", targetNamespace = WsConstants.NS)
public interface ImageWebService {

	/**
	 * 获取图片.
	 */
	public ImageResult getImage();
}
