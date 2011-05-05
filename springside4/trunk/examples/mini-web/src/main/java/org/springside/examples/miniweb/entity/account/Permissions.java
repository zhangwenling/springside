package org.springside.examples.miniweb.entity.account;

public enum Permissions {

	USER_VIEW("user:view"), USER_MANAGE("user:manage"),

	GROUP_VIEW("group:view"), GROUP_MANAGE("group:manage");

	private String value;

	Permissions(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
