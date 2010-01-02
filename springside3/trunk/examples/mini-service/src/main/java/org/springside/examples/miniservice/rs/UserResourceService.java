package org.springside.examples.miniservice.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.dozer.DozerBeanMapper;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.rs.dto.UserDTO;
import org.springside.examples.miniservice.service.user.UserManager;

/**
 * 
 * @author calvin
 */
@Path("/users")
public class UserResourceService {

	@Autowired
	private UserManager userManager;
	@Autowired
	private DozerBeanMapper dozer;

	/**
	 * 获取所有用户, 返回List<UserDTO>.
	 */
	@GET
	@Produces("application/json")
	public Response getAllUser() {
		try {
			List<User> userEntityList = userManager.getAllUser();
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();
			for (User userEntity : userEntityList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}
			return Response.ok(userDTOList).build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	/**
	 * 获取用户, 返回UserDTO.
	 */
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Response getUser(@PathParam("id") Long id) {
		try {
			User entity = userManager.getUser(id);
			UserDTO dto = dozer.map(entity, UserDTO.class);
			return Response.ok(dto).build();
		} catch (ObjectNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	/**
	 * 创建用户.
	 */
	@POST
	@Consumes("application/json")
	public Response createUser(UserDTO user) {
		try {
			User userEntity = dozer.map(user, User.class);
			userManager.saveUser(userEntity);
			return Response.ok().entity(userEntity.getId()).build();
		} catch (RuntimeException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
}
