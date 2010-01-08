package org.springside.examples.showcase.ws.server;

import javax.jws.WebService;

import org.springside.examples.showcase.ws.server.result.GetAllUserResult;

/**
 * JAX-WS2.0的WebService接口定义类.
 * 
 * @author calvin
 */
@WebService(name = "UserService", targetNamespace = WsConstants.NS)
public interface UserWebService {
	/**
	 * 显示所有用户.
	 */
	public GetAllUserResult getAllUser();
}
