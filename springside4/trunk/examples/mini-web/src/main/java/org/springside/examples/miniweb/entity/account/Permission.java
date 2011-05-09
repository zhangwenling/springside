package org.springside.examples.miniweb.entity.account;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Permission {

	USER_VIEW("user:view", "查看用戶"), USER_EDIT("user:edit", "修改用户"),

	GROUP_VIEW("group:view", "查看权限组"), GROUP_EDIT("group:edit", "修改权限组");

	public String value;
	public String displayName;
	private static Map<String, Permission> valueMap = Maps.newHashMap();

	static {
		for (Permission permission : Permission.values()) {
			valueMap.put(permission.value, permission);
		}
	}

	Permission(String value, String displayName) {
		this.displayName = displayName;
		this.value = value;
	}

	public static Permission parse(String value) {
		return valueMap.get(value);
	}
}
