package org.springside.examples.miniservice.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.service.user.UserManager;
import org.springside.examples.miniservice.ws.UserWebService;
import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.AuthUserResult;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetAllUserResult;
import org.springside.examples.miniservice.ws.result.GetUserResult;
import org.springside.examples.miniservice.ws.result.WSResult;

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
			List<User> userEntityList = userManager.getAllUser();
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();
			for (User userEntity : userEntityList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}

			GetAllUserResult result = new GetAllUserResult();
			result.setUserList(userDTOList);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildExceptionResult(GetAllUserResult.class, e);
		}
	}

	/**
	 * @see UserWebService#getUser()
	 */
	public GetUserResult getUser(Long id) {

		//校验请求参数
		if (id == null) {
			logger.error("id参数为空");
			return WSResult.buildExceptionResult(GetUserResult.class, WSResult.PARAMETER_ERROR, "id参数为空");
		}

		//获取用户
		try {
			User entity = userManager.getUser(id);
			UserDTO dto = dozer.map(entity, UserDTO.class);

			GetUserResult result = new GetUserResult();
			result.setUser(dto);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildExceptionResult(GetUserResult.class, e);
		}

	}

	/**
	 * @see UserWebService#createUser(UserDTO)
	 */
	public CreateUserResult createUser(UserDTO user) {

		//校验请求参数
		if (user == null) {
			logger.error("user参数为空");
			return WSResult.buildExceptionResult(CreateUserResult.class, WSResult.PARAMETER_ERROR, "user参数为空");
		}

		//保存用户
		try {
			User userEntity = dozer.map(user, User.class);
			userManager.saveUser(userEntity);

			CreateUserResult result = new CreateUserResult();
			result.setUserId(userEntity.getId());
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return WSResult.buildExceptionResult(CreateUserResult.class, e);
		}
	}

	/**
	 * @see UserWebService#authUser(String, String)
	 */
	public AuthUserResult authUser(String loginName, String password) {

		//校验请求参数
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			logger.error("用户名或密码为空");
			return WSResult.buildExceptionResult(AuthUserResult.class, WSResult.PARAMETER_ERROR, "用户名或密码为空");
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
			return WSResult.buildExceptionResult(AuthUserResult.class, e);
		}
	}
}
