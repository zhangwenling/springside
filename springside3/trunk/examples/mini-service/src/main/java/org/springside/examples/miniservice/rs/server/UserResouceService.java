package org.springside.examples.miniservice.rs.server;

import java.net.URI;
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

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.rs.dto.UserDTO;
import org.springside.examples.miniservice.service.account.AccountManager;

/**
 * User资源的REST服务.
 * 
 * @author calvin
 */
@Component
@Path("/users")
public class UserResouceService {

	private static Logger logger = LoggerFactory.getLogger(DepartmentResourceService.class);

	@Context
	private UriInfo uriInfo;

	private AccountManager accountManager;

	private Validator validator;

	private DozerBeanMapper dozer;

	/**
	 * 创建用户, 请求数据为JSON/XML格式编码的DTO, 返回表示所创建用户的URI.
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + JerseyServerUtils.CHARSET })
	public Response createUser(UserDTO user) {

		Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(user);
		if (!constraintViolations.isEmpty()) {
			ConstraintViolation<UserDTO> violation = constraintViolations.iterator().next();
			String message = violation.getPropertyPath() + " " + violation.getMessage();
			throw JerseyServerUtils.buildException(logger, Status.BAD_REQUEST.getStatusCode(), message);
		}

		try {

			User userEntity = dozer.map(user, User.class);

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

	@GET
	@Path("auth")
	public String authUser(@QueryParam("loginName") String loginName, @QueryParam("password") String password) {
		try {
			Boolean result = accountManager.authenticate(loginName, password);
			return result.toString();
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

	@Autowired
	public void setDozer(DozerBeanMapper dozer) {
		this.dozer = dozer;
	}
}
