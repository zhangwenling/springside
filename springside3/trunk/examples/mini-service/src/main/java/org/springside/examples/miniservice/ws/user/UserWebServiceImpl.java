package org.springside.examples.miniservice.ws.user;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.entity.user.User;
import org.springside.examples.miniservice.service.user.UserManager;
import org.springside.examples.miniservice.ws.Constants;
import org.springside.examples.miniservice.ws.WSResult;
import org.springside.examples.miniservice.ws.WebServiceSupport;
import org.springside.examples.miniservice.ws.user.dto.CreateUserResult;
import org.springside.examples.miniservice.ws.user.dto.GetAllUserResult;
import org.springside.examples.miniservice.ws.user.dto.UserDTO;

/**
 * WebService实现类.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称,endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserWebService", portName = "UserWebServicePort", endpointInterface = "org.springside.examples.miniservice.ws.user.UserWebService", targetNamespace = Constants.NS)
public class UserWebServiceImpl extends WebServiceSupport implements UserWebService {
	@Autowired
	private UserManager userManager;

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
				userDTOList.add((UserDTO) dozer.map(userEntity, UserDTO.class));
			}
			result.setUserList(userDTOList);
		} catch (RuntimeException e) {
			result.setSystemError();
			logger.error(e.getMessage(), e);
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
			User userEntity = (User) dozer.map(user, User.class);
			userManager.saveUser(userEntity);
			result.setUserId(userEntity.getId());
		} catch (RuntimeException e) {
			result.setSystemError();
			logger.error(e.getMessage(), e);
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
			result.setSystemError();
			logger.error(e.getMessage(), e);
		}

		return result;
	}
}
