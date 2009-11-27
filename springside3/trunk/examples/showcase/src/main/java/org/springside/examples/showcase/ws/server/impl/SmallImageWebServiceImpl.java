package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.jws.WebService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springside.examples.showcase.ws.server.api.SmallImageWebService;
import org.springside.examples.showcase.ws.server.api.WsConstants;
import org.springside.examples.showcase.ws.server.api.result.SmallImageResult;
import org.springside.examples.showcase.ws.server.api.result.WSResult;

/**
 * SmallImageWebService实现类.
 * 
 * @see SmallImageWebService
 * 
 * @author calvin
 */
@WebService(serviceName = WsConstants.SMALL_IMAGE_SERVICE, portName = "SmallImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.SmallImageWebService", targetNamespace = WsConstants.NS)
public class SmallImageWebServiceImpl implements SmallImageWebService, ApplicationContextAware {

	private ApplicationContext cxt;

	/**
	 * @see SmallImageWebService#getImage()
	 */
	public SmallImageResult getImage() {
		SmallImageResult result = new SmallImageResult();
		try {
			Resource imageResource = cxt.getResource("/img/logo.jpg");
			byte[] imageBytes = IOUtils.toByteArray(imageResource.getInputStream());
			result.setImageData(imageBytes);
		} catch (IOException e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
		}

		return result;
	}

	public void setApplicationContext(ApplicationContext cxt) throws BeansException {
		this.cxt = cxt;
	}
}
