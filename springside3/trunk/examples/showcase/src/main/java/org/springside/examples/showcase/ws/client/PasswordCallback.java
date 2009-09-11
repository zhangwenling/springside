package org.springside.examples.showcase.ws.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * 为客户端发起的请求设置WS-Security要求的密码.
 * 
 * @author calvin
 */
public class PasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		//为admin用户设置密码"admin"
		if (pc.getIdentifier().equals("admin")) {
			pc.setPassword("admin");
		}
	}
}
