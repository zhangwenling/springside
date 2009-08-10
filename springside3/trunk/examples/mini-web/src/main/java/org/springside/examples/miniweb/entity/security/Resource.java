package org.springside.examples.miniweb.entity.security;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.examples.miniweb.entity.IdEntity;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 受保护的资源.
 * 
 * @author calvin
 */
@Entity
@Table(name = "SS_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource extends IdEntity {

	//resourceType常量
	public static final String URL_TYPE = "url";
	public static final String MENU_TYPE = "menu";

	private String resourceType; //资源类型
	private String value; //资源标识
	private double position; //资源在SpringSecurity中的校验顺序字段

	private Set<Authority> authorities = new LinkedHashSet<Authority>(); //可访问该资源的授权

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	@ManyToMany
	@JoinTable(name = "SS_RESOURCE_AUTHORITY", joinColumns = { @JoinColumn(name = "RESOURCE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@Fetch(FetchMode.JOIN)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Transient
	public String getAuthNames() {
		return ReflectionUtils.fetchElementPropertyToString(authorities, "name", ",");
	}
}
