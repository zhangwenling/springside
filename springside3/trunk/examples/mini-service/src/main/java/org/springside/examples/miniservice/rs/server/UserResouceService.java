package org.springside.examples.miniservice.rs.server;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.rs.dto.UserDTO;
import org.springside.examples.miniservice.service.account.AccountManager;
import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.modules.utils.mapping.DozerUtils;

import com.google.common.collect.Maps;

/**
 * User资源的REST服务.
 * 
 * @author calvin
 */
@Component
@Path("/users")
public class UserResouceService {

	private static Logger logger = LoggerFactory.getLogger(UserResouceService.class);

	@Context
	private UriInfo uriInfo;

	private AccountManager accountManager;

	private Validator validator;

	@GET
	@Path("search")
	public List<UserDTO> searchUser(@QueryParam("loginName") String loginName, @QueryParam("name") String name) {
		try {
			Map<String, String> parameters = Maps.newHashMap();
			parameters.put("loginName", loginName);
			parameters.put("name", name);
			List<User> entityList = accountManager.searchUser(parameters);
			return DozerUtils.mapList(entityList, UserDTO.class);
		} catch (RuntimeException e) {
			throw JerseyServerUtils.buildException(logger, e);
		}
	}

	/**
	 * 创建用户, 请求数据为JSON/XML格式编码的DTO, 返回表示所创建用户的URI.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public Response createUser(UserDTO user) {

		Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(user);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<UserDTO> violation = constraintViolations.iterator().next();
			String message = violation.getPropertyPath() + " " + violation.getMessage();
			throw JerseyServerUtils.buildException(logger, Status.BAD_REQUEST.getStatusCode(), message);
		}

		try {
			User userEntity = DozerUtils.map(user, User.class);

			Long id = accountManager.saveUser(userEntity);

			URI createdUri = uriInfo.getAbsolutePathBuilder().path(id.toString()).build();

			return Response.created(createdUri).build();
		} catch (DataIntegrityViolationException e) {
			String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
			throw JerseyServerUtils.buildException(logger, Status.BAD_REQUEST.getStatusCode(), message);
		} catch (RuntimeException e) {
			throw JerseyServerUtils.buildException(logger, e);
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}
