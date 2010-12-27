package org.springside.examples.miniservice.ws.exception;

/**
 * 用于客户端将 WSResult转换为异常
 * 
 * @author badqiu
 *
 */
public class WebServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public WebServiceException() {
		super();
	}

	public WebServiceException(String errorCode,String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public WebServiceException(String errorCode,String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public WebServiceException(String errorCode,Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return "errorCode:"+errorCode+" message:"+super.toString();
	}

}
