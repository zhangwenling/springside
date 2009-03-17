package org.springside.examples.miniweb.entity.user;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springside.examples.miniweb.entity.IdEntity;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 角色.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义二级缓存. 
 * 
 * @author calvin
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "ROLES")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {

	private String name;

	private Set<Authority> auths = new LinkedHashSet<Authority>(); //有序的关联对象集合

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//避免定义CascadeType.REMOVE, 否则删除权限时会连带删除拥有它的角色.
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	//多对多定义.
	@JoinTable(name = "ROLES_AUTHORITIES", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	//集合按id排序.
	@OrderBy("id")
	//集合中对象的id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Authority> getAuths() {
		return auths;
	}

	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	//非持久化属性.
	@Transient
	public String getAuthNames() throws Exception {
		return ReflectionUtils.fetchElementPropertyToString(auths, "displayName", ", ");
	}

	//非持久化属性.
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIds() throws Exception {
		return ReflectionUtils.fetchElementPropertyToList(auths, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
