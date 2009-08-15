#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.data;

import org.apache.commons.lang.RandomStringUtils;
import ${package}.entity.security.Authority;
import ${package}.entity.security.Resource;
import ${package}.entity.security.Role;
import ${package}.entity.security.User;

/**
 * 测试用户数据生成.
 * 
 * @author calvin
 */
public class SecurityData {
	
	public static String random() {
		return RandomStringUtils.randomAlphanumeric(5);
	}

	public static User getRandomUser() {
		String userName = "User" + random();

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword("passwd");
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static Role getRandomRole() {
		Role role = new Role();
		role.setName("Role" + random());

		return role;
	}

	public static Role getAdminRole() {
		Role role = new Role();
		role.setId(1L);

		return role;
	}

	public static Authority getRandomAuthority() {
		Authority authority = new Authority();
		String authName = "Authority" + random();
		authority.setName(authName);
		authority.setDisplayName(authName);

		return authority;
	}

	public static Resource getRandomResource() {
		Resource resource = new Resource();
		resource.setValue("Resource" + random());
		resource.setResourceType(Resource.URL_TYPE);
		resource.setPosition(100);

		return resource;
	}
}
