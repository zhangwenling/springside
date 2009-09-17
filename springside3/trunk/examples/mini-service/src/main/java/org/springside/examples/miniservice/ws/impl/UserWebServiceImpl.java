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
import org.springside.examples.miniservice.ws.api.Constants;
import org.springside.examples.miniservice.ws.api.UserWebService;
import org.springside.examples.miniservice.ws.api.dto.UserDTO;
import org.springside.examples.miniservice.ws.api.result.CreateUserResult;
import org.springside.examples.miniservice.ws.api.result.GetAllUserResult;
import org.springside.examples.miniservice.ws.api.result.WSResult;

/**
 * WebService实现类.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称,endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "org.springside.examples.miniservice.ws.api.UserWebService", targetNamespace = Constants.NS)
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
		GetAllUserResult result = new GetAllUserResult();

		//获取User列表并转换为UserDTO列表.
		try {
			List<User> userList = userManager.getAllUser();
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();
			for (User userEntity : userList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}
			result.setUserList(userDTOList);
		} catch (RuntimeException e) {
			handleException(e, result);
		}
		return result;
	}

	/**
	 * @see UserWebService#createUser(UserDTO)
	 */
	public CreateUserResult createUser(UserDTO user) {
		CreateUserResult result = new CreateUserResult();

		//校验请求参数
		if (user == null) {
			result.setResult(WSResult.PARAMETER_ERROR, "user参数为空");
			logger.warn(result.getMessage());
			return result;
		}

		//保存用户
		try {
			User userEntity = dozer.map(user, User.class);
			userManager.saveUser(userEntity);
			result.setUserId(userEntity.getId());
		} catch (RuntimeException e) {
			handleException(e, result);
		}

		return result;
	}

	/**
	 * @see UserWebService#authUser(String, String)
	 */
	public WSResult authUser(String loginName, String password) {
		WSResult result = new WSResult();

		//校验请求参数
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			result.setResult(WSResult.PARAMETER_ERROR, "用户名或密码为空");
			logger.warn(result.getMessage());
			return result;
		}

		//认证
		try {
			if (userManager.authenticate(loginName, password)) {
				result.setCode(WSResult.SUCCESS);
			} else {
				result.setCode(WSResult.AUTH_ERROR);
			}
		} catch (RuntimeException e) {
			handleException(e, result);
		}

		return result;
	}

	/**
	 * 默认的异常处理函数.
	 */
	private void handleException(Exception e, WSResult result) {
		result.setDefaultError();
		logger.error(e.getMessage(), e);
	}
}
