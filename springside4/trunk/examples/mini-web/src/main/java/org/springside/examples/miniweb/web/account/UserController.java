package org.springside.examples.miniweb.web.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.examples.miniweb.service.account.AccountManager;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PageRequest;
import org.springside.modules.orm.PropertyFilter;

/**
 * Urls:
 * list   page        : GET  /account/user/ or /account/user/list
 * create page        : GET  /account/user/create
 * create action      : POST /account/user/save
 * update page        : GET  /account/user/update/{id}
 * update action      : POST /account/user/save/{id}
 * delete action      : POST /account/user/delete/{id}
 * checkLoginName ajax: GET  /account/user/checkLoginName?oldLoginName=a&loginName=b
 * 
 * @author calvin
 *
 */
@Controller
@RequestMapping(value = "/account/user")
public class UserController {

	private AccountManager accountManager;

	private GroupListEditor groupListEditor;

	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(List.class, "groupList", groupListEditor);
	}

	@RequestMapping(value = { "list", "" })
	public String list(Model model) {
		Page<User> users = accountManager.searchUser(new PageRequest(1, 100), new ArrayList<PropertyFilter>());
		model.addAttribute("users", users.getResult());
		return "account/userList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("groups", accountManager.getAllGroup());
		return "account/userForm";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String create(User user) {
		accountManager.saveUser(user);
		return "redirect:/account/user/";
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id) {
		accountManager.deleteUser(id);
		return "redirect:/account/user/";
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

	@Autowired
	public void setGroupListEditor(GroupListEditor groupListEditor) {
		this.groupListEditor = groupListEditor;
	}

}
