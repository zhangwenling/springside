package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.jws.WebService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springside.examples.showcase.ws.server.api.LittleImageWebService;
import org.springside.examples.showcase.ws.server.api.WsConstants;
import org.springside.examples.showcase.ws.server.api.result.LittleImageResult;
import org.springside.examples.showcase.ws.server.api.result.WSResult;

/**
 * LittleImageWebService实现类.
 * 
 * @see LittleImageWebService
 * 
 * @author calvin
 */
@WebService(serviceName = "LittleImageService", portName = "LittleImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.LittleImageWebService", targetNamespace = WsConstants.NS)
public class LittleImageWebServiceImpl implements LittleImageWebService, ApplicationContextAware {

	private ApplicationContext cxt;

	/**
	 * @see LittleImageWebService#getLittleImage()
	 */
	public LittleImageResult getImage() {
		LittleImageResult result = new LittleImageResult();
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
