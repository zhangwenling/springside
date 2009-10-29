package org.springside.examples.showcase.ws.server.api.result;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.showcase.ws.server.api.WsConstants;

/**
 * GetImage方法的返回结果类型. 
 * 
 * image采用base64Binary编码.
 * 
 * @author calvin
 */
@XmlType(name = "ImageResult", namespace = WsConstants.NS)
public class ImageResult extends WSResult {

	private static final long serialVersionUID = 8375875101365439245L;

	private byte[] image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
