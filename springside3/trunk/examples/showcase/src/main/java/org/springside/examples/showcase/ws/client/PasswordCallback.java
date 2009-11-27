package org.springside.examples.showcase.ws.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * 为客户端发起的请求设置WS-Security的明文密码的Handler.
 * 
 * 
 * @author calvin
 */
public class PasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		//为admin用户设置密码"admin", 为其他用户统一设置密码"user".
		//设置明文密码, 框架会按需要负责后面的Digest等加工操作.

		if (pc.getIdentifier().equals("admin")) {
			pc.setPassword("admin");
		} else {
			pc.setPassword("user");
		}
	}
}
