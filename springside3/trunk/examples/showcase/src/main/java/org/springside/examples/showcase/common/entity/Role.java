package org.springside.examples.showcase.common.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 角色.
 * 
 * 使用JPA annotation定义ORM关系.
 * 
 * @author calvin
 */
@Entity
@Table(name = "ROLES")
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
