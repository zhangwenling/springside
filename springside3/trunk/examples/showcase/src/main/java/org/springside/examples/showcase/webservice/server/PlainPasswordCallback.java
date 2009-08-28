package org.springside.examples.showcase.webservice.server;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;

public class PlainPasswordCallback implements CallbackHandler {

	@Autowired
	private UserManager userManager;

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

		User user = userManager.getUserByLoginName(pc.getIdentifier());
		if (!user.getPassword().equals(pc.getPassword())) {
			throw new IOException("wrong password " + pc.getPassword() + " of " + pc.getIdentifier());
		}
	}
}
