package org.springside.examples.miniweb.entity.security;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springside.examples.miniweb.entity.IdEntity;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 资源.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义二级缓存. 
 * 
 * @author calvin
 */
@Entity
@Table(name = "RESOURCES")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource extends IdEntity {

	private String type; //资源类型,可为url,menu,method等

	private String value;

	private Set<Authority> auths = new LinkedHashSet<Authority>(); //有序的关联对象集合

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "RESOURCES_AUTHORITIES", joinColumns = { @JoinColumn(name = "RESOURCE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public Set<Authority> getAuths() {
		return auths;
	}

	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	@Transient
	public String getAuthNames() throws Exception {
		return ReflectionUtils.fetchElementPropertyToString(auths, "name", ",");
	}
}
