package org.springside.examples.showcase.ws.server.api;

import javax.jws.WebService;

import org.springside.examples.showcase.ws.server.api.result.SmallImageResult;

/**
 * 演示以Base64Binary直接编码整个byte数组的二进制数据传输方式.
 * 
 * @author calvin
 */
@WebService(name = WsConstants.SMALL_IMAGE_SERVICE, targetNamespace = WsConstants.NS)
public interface SmallImageWebService {

	/**
	 * 获取小图片,使用Base64直接传输byte[].
	 */
	public SmallImageResult getImage();
}
