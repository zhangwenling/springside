package org.springside.examples.showcase.security;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.entity.Role;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;

import com.google.common.collect.Sets;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * 演示扩展SpringSecurity的User类加入loginTime信息.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserManager userManager;

	/**
	 * 获取用户Detail信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		User user = userManager.findUserByLoginName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + userName + " 不存在");
		}

		GrantedAuthority[] grantedAuths = obtainGrantedAuthorities(user);

		//showcase的User类中无以下属性,暂时全部设为true.
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		OperatorDetails userDetails = new OperatorDetails(user.getLoginName(), user.getShaPassword(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, grantedAuths);
		//加入登录时间信息
		userDetails.setLoginTime(new Date());
		return userDetails;
	}

	/**
	 * 获得用户所有角色的权限.
	 */
	private GrantedAuthority[] obtainGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
		for (Role role : user.getRoleList()) {
			authSet.add(new GrantedAuthorityImpl("ROLE_" + role.getName()));
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
