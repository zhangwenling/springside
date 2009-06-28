package org.springside.examples.showcase.security;

import java.util.Date;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;

public class Operator extends User {
	
		private static final long serialVersionUID = 1919464185097508773L;

	private Date loginTime; 

	public Operator(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities)
			throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}
