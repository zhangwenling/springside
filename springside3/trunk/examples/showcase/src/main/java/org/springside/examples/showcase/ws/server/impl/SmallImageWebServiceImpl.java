package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.jws.WebService;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
@WebService(serviceName = "SmallImageService", portName = "SmallImageServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.SmallImageWebService", targetNamespace = WsConstants.NS)
public class SmallImageWebServiceImpl implements SmallImageWebService, ApplicationContextAware {

	private ApplicationContext applicationContext;

	/**
	 * @see SmallImageWebService#getImage()
	 */
	public SmallImageResult getImage() {
		SmallImageResult result = new SmallImageResult();
		InputStream is = null;
		try {
			//采用applicationContext的getResource()函数获取Web应用中的文件.
			is = applicationContext.getResource("/img/logo.jpg").getInputStream();
			//读取内容到字节数组.
			byte[] imageBytes = IOUtils.toByteArray(is);
			result.setImageData(imageBytes);
		} catch (IOException e) {
			result.setResult(WSResult.IMAGE_ERROR, "Image reading error.");
		}finally{
			IOUtils.closeQuietly(is);
		}

		return result;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
