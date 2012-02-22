package org.springside.examples.miniweb.web.account;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.service.account.AccountManager;
import org.springside.modules.utils.Collections3;

@Component
public class GroupListEditor extends PropertyEditorSupport {
	private AccountManager accountManager;

	public void setAsText(String text) throws IllegalArgumentException {
		String[] ids = StringUtils.split(text, ",");
		List<Group> groups = new ArrayList<Group>();
		for (String id : ids) {
			groups.add(accountManager.getGroup(Long.valueOf(id)));
		}
		setValue(groups);
	}

	@Override
	public String getAsText() {
		return Collections3.extractToString((List) getValue(), "id", ",");
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
