package org.springside.examples.showcase.ws.server.impl;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.jws.WebService;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
@WebService(serviceName = "LargeImageService", portName = "LargeImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.LargeImageWebService", targetNamespace = WsConstants.NS)
public class LargeImageWebServiceImpl implements LargeImageWebService, ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * @see LargeImageWebService#getImage()
	 */
	public LargeImageResult getImage() {
		LargeImageResult result = new LargeImageResult();

		try {
			//采用applicationContext获取Web应用中的文件.
			File image = applicationContext.getResource("/img/logo.jpg").getFile();

			//采用activation的DataHandler实现Streaming传输.
			DataSource dataSource = new FileDataSource(image);
			DataHandler dataHandler = new DataHandler(dataSource);
			result.setImageData(dataHandler);
		} catch (IOException e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
		}

		return result;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
