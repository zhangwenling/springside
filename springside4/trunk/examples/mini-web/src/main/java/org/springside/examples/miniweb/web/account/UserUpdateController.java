package org.springside.examples.miniweb.web.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.account.AccountManager;

/**
 * 使用@ModelAttribute,实现Struts2 Preparable二次绑定的效果。 因为与其他的Action不兼容，独立一个Controller.
 * 
 * @author calvin
 *
 */
@Controller
@RequestMapping(value = "/account/user/")
public class UserUpdateController {

	private AccountManager accountManager;

	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(List.class, "groupList", new GroupListEditor());
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(Model model) {
		model.addAttribute("groups", accountManager.getAllGroup());
		return "account/userForm";
	}

	@RequestMapping(value = "save/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute("user") User user, BindingResult result) {
		accountManager.saveUser(user);
		return "redirect:/account/user/";
	}

	@ModelAttribute("user")
	public User getAccount(@PathVariable("id") Long id) {
		return accountManager.getUser(id);
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
