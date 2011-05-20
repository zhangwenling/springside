package org.springside.examples.miniweb.web.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.account.AccountManager;

@Controller
@RequestMapping(value = "/account/user")
public class UserController {

	private AccountManager accountManager;

	@RequestMapping(value = { "list", "" })
	public String list() {
		return "account/userList";
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable Long id, Model model) {
		User user = accountManager.getUser(id);
		model.addAttribute(user);
		return "account/userForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		return "account/userForm";
	}

	@ModelAttribute("groups")
	public Map<Long, String> getGroups() {
		List<Group> allRoles = accountManager.getAllGroup();
		return null;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
