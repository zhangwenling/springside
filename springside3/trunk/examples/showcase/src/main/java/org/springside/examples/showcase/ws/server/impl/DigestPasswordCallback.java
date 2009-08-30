package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 对WS-Security中Digest式密码的处理Handler,根据用户名查出数据库中用户明文密码,交由框架进行Digest处理与比较.
 * 
 * @author calvin
 */
public class DigestPasswordCallback implements CallbackHandler {

	@Autowired
	private UserManager userManager;

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		User user = userManager.getUserByLoginName(pc.getIdentifier());
		pc.setPassword(user.getPlainPassword());
	}
}
