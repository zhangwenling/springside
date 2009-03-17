#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity.user;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ${package}.entity.IdEntity;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系.
 * 使用Hibernate annotation定义二级缓存.
 * 集中定义了所有以用户为主体的HQL.
 * 
 * @author calvin
 */
@Entity
//表名与类名不相同时重新定义表名.
@Table(name = "USERS")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {

	// 统一定义所有以用户为主体的HQL.
	public static final String AUTH_HQL = "select count(u) from User u where u.loginName=? and u.password=?";

	private String loginName;
	private String password;
	private String name;
	private String email;

	private Set<Role> roles = new LinkedHashSet<Role>(0); //有序的关联对象集合

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//避免定义CascadeType.REMOVE, 否则删除角色时会连带删除拥有它的用户.
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	//多对多定义.
	@JoinTable(name = "USERS_ROLES", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	//集合按id排序.
	@OrderBy("id")
	//集合中对象的id的缓存.
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}