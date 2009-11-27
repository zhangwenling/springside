package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springside.examples.showcase.ws.server.api.LargeImageWebService;
import org.springside.examples.showcase.ws.server.api.WsConstants;
import org.springside.examples.showcase.ws.server.api.result.LargeImageResult;
import org.springside.examples.showcase.ws.server.api.result.WSResult;

/**
 * LargeImageWebService实现类.
 * 
 * @see LargeImageWebService
 * 
 * @author calvin
 */
@WebService(serviceName = WsConstants.LARGE_IMAGE_SERVICE, portName = "LargeImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.LargeImageWebService", targetNamespace = WsConstants.NS)
public class LargeImageWebServiceImpl implements LargeImageWebService, ApplicationContextAware {

	private ApplicationContext cxt;

	/**
	 * @see LargeImageWebService#getImage()
	 */
	public LargeImageResult getImage() {
		LargeImageResult result = new LargeImageResult();

		try {
			Resource imageResource = cxt.getResource("/img/logo.jpg");
			DataSource dataSource = new FileDataSource(imageResource.getFile());
			DataHandler dataHandler = new DataHandler(dataSource);
			result.setImageData(dataHandler);
		} catch (IOException e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
		}

		return result;
	}

	public void setApplicationContext(ApplicationContext cxt) throws BeansException {
		this.cxt = cxt;
	}
}
