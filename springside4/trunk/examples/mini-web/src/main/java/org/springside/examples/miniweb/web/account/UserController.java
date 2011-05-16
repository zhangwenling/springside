package org.springside.examples.miniweb.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.account.AccountManager;

@Controller
@RequestMapping(value = "/account")
public class UserController {

	private AccountManager accountManager;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String input(@PathVariable Long id, Model model) {
		User user;
		if (id == null) {
			user = new User();
		} else {
			user = accountManager.getUser(id);
		}

		model.addAttribute(user);
		return "account/user-input";
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
