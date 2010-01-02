package org.springside.examples.miniservice.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.rs.dto.UserDTO;
import org.springside.examples.miniservice.service.user.UserManager;

/**
 * 
 * @author calvin
 */
@Path("/users")
@Produces("application/json")
public class UserResourceService {

	@Autowired
	private UserManager userManager;
	@Autowired
	private DozerBeanMapper dozer;

	/**
	 * 显示所有用户.
	 */
	@GET
	@Produces("application/json")
	public List<UserDTO> getAllUser() {
		List<User> userEntityList = userManager.getAllUser();
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		for (User userEntity : userEntityList) {
			userDTOList.add(dozer.map(userEntity, UserDTO.class));
		}
		return userDTOList;
	}

	@GET
	@Path("{id}")
	@Produces("application/json")
	public UserDTO getUser(@PathParam("id") Long id) {
		User entity = userManager.getUser(id);
		UserDTO dto = dozer.map(entity, UserDTO.class);
		return dto;
    }
}
