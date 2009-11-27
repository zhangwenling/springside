package org.springside.examples.showcase.ws.server.api;

import javax.jws.WebService;

import org.springside.examples.showcase.ws.server.api.result.LargeImageResult;

/**
 * 演示以MTOM附件协议传输Streaming DataHandler的二进制数据传输的方式. 
 * 
 * @author calvin
 */
@WebService(name = WsConstants.LARGE_IMAGE_SERVICE, targetNamespace = WsConstants.NS)
public interface LargeImageWebService {

	public LargeImageResult getImage();
}
