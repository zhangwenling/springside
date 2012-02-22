package org.springside.examples.miniweb.web.account;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.service.account.AccountManager;
import org.springside.modules.utils.Collections3;

public class GroupListEditor extends PropertyEditorSupport {
	private AccountManager accountManager;

	public void GroupListEditor(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

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

}
