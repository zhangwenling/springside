package org.springside.examples.miniservice.ws.impl;

import java.util.List;

import javax.jws.WebService;

import org.dozer.DozerBeanMapper;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.service.account.UserManager;
import org.springside.examples.miniservice.ws.UserWebService;
import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.AuthUserResult;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetAllUserResult;
import org.springside.examples.miniservice.ws.result.GetUserResult;
import org.springside.examples.miniservice.ws.result.WSResult;

import com.google.common.collect.Lists;

/**
 * WebService实现类.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "org.springside.examples.miniservice.ws.UserWebService", targetNamespace = WsConstants.NS)
public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = LoggerFactory.getLogger(UserWebServiceImpl.class);

	@Autowired
	private UserManager userManager;
	@Autowired
	private DozerBeanMapper dozer;

	/**
	 * @see UserWebService#getAllUser()
	 */
	public GetAllUserResult getAllUser() {
		//获取User列表并转换为UserDTO列表.
		try {
			List<User> userEntityList = userManager.getAllLoadedUser();
			List<UserDTO> userDTOList = Lists.newArrayList();

			for (User userEntity : userEntityList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}

			GetAllUserResult result = new GetAllUserResult();
			result.setUserList(userDTOList);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildDefaultErrorResult(GetAllUserResult.class);
		}
	}

	/**
	 * @see UserWebService#getUser()
	 */
	public GetUserResult getUser(Long id) {
		//校验请求参数
		try {
			Assert.notNull(id, "id参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return WSResult.buildResult(GetUserResult.class, WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//获取用户
		try {
			User entity = userManager.getLoadedUser(id);
			UserDTO dto = dozer.map(entity, UserDTO.class);

			GetUserResult result = new GetUserResult();
			result.setUser(dto);
			return result;
		} catch (ObjectNotFoundException e) {
			String message = "用户不存在(id:" + id + ")";
			logger.error(message, e);
			return WSResult.buildResult(GetUserResult.class, WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildDefaultErrorResult(GetUserResult.class);
		}
	}

	/**
	 * @see UserWebService#createUser(UserDTO)
	 */
	public CreateUserResult createUser(UserDTO user) {
		//校验请求参数
		try {
			Assert.notNull(user, "用户参数为空");
			Assert.hasText(user.getLoginName(), "新建用户登录名参数为空");
			Assert.isNull(user.getId(), "新建用户ID参数必须为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return WSResult.buildResult(CreateUserResult.class, WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//保存用户
		try {
			User userEntity = dozer.map(user, User.class);
			userManager.saveUser(userEntity);

			CreateUserResult result = new CreateUserResult();
			result.setUserId(userEntity.getId());
			return result;
		} catch (ConstraintViolationException e) {
			String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
			logger.error(message, e);
			return WSResult.buildResult(CreateUserResult.class, WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildDefaultErrorResult(CreateUserResult.class);
		}
	}

	/**
	 * @see UserWebService#authUser(String, String)
	 */
	public AuthUserResult authUser(String loginName, String password) {

		//校验请求参数
		try {
			Assert.hasText(loginName, "登录名参数为空");
			Assert.hasText(password, "密码参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return WSResult.buildResult(AuthUserResult.class, WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//认证
		try {
			AuthUserResult result = new AuthUserResult();
			if (userManager.authenticate(loginName, password)) {
				result.setValid(true);
			} else {
				result.setValid(false);
			}
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildDefaultErrorResult(AuthUserResult.class);
		}
	}
}
