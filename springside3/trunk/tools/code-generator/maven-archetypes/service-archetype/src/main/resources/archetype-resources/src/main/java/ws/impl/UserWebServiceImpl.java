#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.entity.user.User;
import ${package}.service.user.UserManager;
import ${package}.ws.api.UserWebService;
import ${package}.ws.api.WsConstants;
import ${package}.ws.api.dto.UserDTO;
import ${package}.ws.api.result.AuthUserResult;
import ${package}.ws.api.result.CreateUserResult;
import ${package}.ws.api.result.GetAllUserResult;
import ${package}.ws.api.result.WSResult;

/**
 * WebService实现类.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "${package}.ws.api.UserWebService", targetNamespace = WsConstants.NS)
public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = LoggerFactory.getLogger(UserWebServiceImpl.class);

	@Autowired
	private UserManager userManager;
	@Autowired
	private DozerBeanMapper dozer;

	/**
	 * @see UserWebService${symbol_pound}getAllUser()
	 */
	public GetAllUserResult getAllUser() {
		GetAllUserResult result = new GetAllUserResult();

		//获取User列表并转换为UserDTO列表.
		try {
			List<User> userEntityList = userManager.getAllUser();
			List<UserDTO> userDTOList = new ArrayList<UserDTO>();
			for (User userEntity : userEntityList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}
			result.setUserList(userDTOList);
		} catch (RuntimeException e) {
			handleException(e, result);
		}
		return result;
	}

	/**
	 * @see UserWebService${symbol_pound}createUser(UserDTO)
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
	 * @see UserWebService${symbol_pound}authUser(String, String)
	 */
	public AuthUserResult authUser(String loginName, String password) {
		AuthUserResult result = new AuthUserResult();

		//校验请求参数
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			result.setResult(WSResult.PARAMETER_ERROR, "用户名或密码为空");
			logger.warn(result.getMessage());
			return result;
		}

		//认证
		try {
			if (userManager.authenticate(loginName, password)) {
				result.setValid(true);
			} else {
				result.setValid(false);
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
