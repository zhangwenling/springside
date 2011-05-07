package org.springside.examples.miniweb.entity.account;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Permission {

	USER_VIEW("user:view", "查看用戶"), USER_EDIT("user:edit", "修改用戶"),

	GROUP_VIEW("group:view", "查看權限組"), GROUP_EDIT("group:edit", "修改權限組");

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
