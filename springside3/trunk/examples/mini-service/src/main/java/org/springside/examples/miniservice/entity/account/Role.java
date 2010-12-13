package org.springside.examples.miniservice.entity.account;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springside.examples.miniservice.entity.IdEntity;

/**
 * 角色.

 * @author calvin
 */
public class Role extends IdEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}