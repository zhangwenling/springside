package org.springside.examples.miniservice.rs;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.dozer.DozerBeanMapper;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger logger = LoggerFactory.getLogger(UserResourceService.class);

	@Autowired
	private UserManager userManager;
	@Autowired
	private DozerBeanMapper dozer;

	/**
	 * 获取所有用户, 返回List<UserDTO>.
	 */
	@GET
	@Produces( { "application/json", "application/xml" })
	public List<UserDTO> getAllUser() {
		try {
			List<User> entityList = userManager.getAllUser();
			List<UserDTO> dtoList = new ArrayList<UserDTO>();
			for (User userEntity : entityList) {
				dtoList.add(dozer.map(userEntity, UserDTO.class));
			}
			return dtoList;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 获取用户, 返回UserDTO.
	 */
	@GET
	@Path("{id}")
	@Produces( { "application/json", "application/xml" })
	public UserDTO getUser(@PathParam("id") Long id) {
		try {
			User entity = userManager.getUser(id);
			UserDTO dto = dozer.map(entity, UserDTO.class);
			return dto;
		} catch (ObjectNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 创建用户.
	 */
	@POST
	@Consumes( { "application/json", "application/xml" })
	public Long createUser(UserDTO user) {
		try {
			User userEntity = dozer.map(user, User.class);
			userManager.saveUser(userEntity);
			return userEntity.getId();
		} catch (ConstraintViolationException e) {
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("唯一性冲突").build());
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).build());
		}
	}
}
