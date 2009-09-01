package org.springside.examples.showcase.ws.server.impl;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 对WS-Security中Digest式密码的处理Handler,根据用户名查出数据库中用户散列密码,对明文密码进行相同的散列后进行比对.
 * 
 * @author calvin
 */
public class PlainPasswordCallback implements CallbackHandler {

	@Autowired
	private UserManager userManager;

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		PasswordEncoder encoder = new ShaPasswordEncoder();
		User user = userManager.getUserByLoginName(pc.getIdentifier());

		//对明文的密码进行sha1散列, 再与数据库中保存的sha1散列密码进行比较.
		if (user == null || !encoder.isPasswordValid(user.getShaPassword(), pc.getPassword(), null))
			throw new IOException("wrong password " + pc.getPassword() + " for " + pc.getIdentifier());
	}
}
