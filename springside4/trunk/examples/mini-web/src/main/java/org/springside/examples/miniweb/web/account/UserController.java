package org.springside.examples.miniweb.web.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.account.AccountManager;

@Controller
@RequestMapping(value = "/account/user")
public class UserController {

	private AccountManager accountManager;

	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(List.class, "groupList", new GroupListEditor());
	}

	@RequestMapping(value = { "list", "" })
	public String list() {
		return "account/userList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute User user, Model model) {
		model.addAttribute("groups", accountManager.getAllGroup());
		return "account/userForm";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String create(User user) {
		accountManager.saveUser(user);
		return "redirect:/account/user/update/" + user.getId();
	}

	@RequestMapping(value = "checkLoginName", method = RequestMethod.GET)
	@ResponseBody
	public String checkLoginName(@RequestParam("oldLoginName") String oldLoginName,
			@RequestParam("loginName") String loginName) {
		if (loginName.equals(oldLoginName)) {
			return "true";
		} else if (accountManager.findUserByLoginName(loginName) == null) {
			return "true";
		}

		return "false";

	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
