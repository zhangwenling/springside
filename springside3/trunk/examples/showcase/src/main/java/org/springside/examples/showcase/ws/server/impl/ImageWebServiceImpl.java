package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.jws.WebService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springside.examples.showcase.ws.server.api.ImageWebService;
import org.springside.examples.showcase.ws.server.api.WsConstants;
import org.springside.examples.showcase.ws.server.api.result.ImageResult;
import org.springside.examples.showcase.ws.server.api.result.WSResult;

/**
 * WebService实现类.
 * 
 * @author calvin
 */
@WebService(serviceName = "ImageService", portName = "ImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.ImageWebService", targetNamespace = WsConstants.NS)
public class ImageWebServiceImpl implements ImageWebService, ApplicationContextAware {

	private ApplicationContext cxt;

	/**
	 * @see ImageWebService#getImage()
	 */
	public ImageResult getImage() {
		ImageResult result = new ImageResult();

		Resource imageResource = cxt.getResource("/img/logo.jpg");
		try {
			byte[] image = IOUtils.toByteArray(imageResource.getInputStream());
			result.setImage(image);
		} catch (IOException e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
		}

		return result;
	}

	public void setApplicationContext(ApplicationContext cxt) throws BeansException {
		this.cxt = cxt;
	}
}
