#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.user;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.entity.user.User;
import ${package}.service.user.UserManager;
import ${package}.ws.Constants;
import ${package}.ws.WSResult;
import ${package}.ws.user.dto.CreateUserResult;
import ${package}.ws.user.dto.GetAllUserResult;
import ${package}.ws.user.dto.UserDTO;
import org.springside.modules.webservice.WebServiceSupport;

/**
 * WebService实现类.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称,endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserWebService", portName = "UserWebServicePort", endpointInterface = "${package}.ws.user.UserWebService", targetNamespace = Constants.NS)
public class UserWebServiceImpl extends WebServiceSupport implements UserWebService {
	@Autowired
	private UserManager userManager;

	/**
	 * @see UserWebService${symbol_pound}getAllUser()
	 */
	public GetAllUserResult getAllUser() {
		GetAllUserResult result = new GetAllUserResult();

		//获取USer列表并转换为UserDTO列表.
		try {
			List<User> userList = userManager.getAll();
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

		try {
			// 从DTO转换到User
			User userEntity = (User) dozer.map(user, User.class);

			// 保存User
			userManager.save(userEntity);
			result.setUserId(userEntity.getId());
		} catch (RuntimeException e) {
			result.setSystemError();
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * @see UserWebService${symbol_pound}authUser(String, String)
	 */
	public WSResult authUser(String loginName, String password) {
		WSResult result = new WSResult();

		//校验请求参数
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			result.setResult(WSResult.PARAMETER_ERROR, "用户名或密码为空");
			logger.warn(result.getMessage());
			return result;
		}

		try {
			if (userManager.authenticate(loginName, password)) {
				result.setCode(WSResult.SUCCESS);
			} else {
				result.setCode(WSResult.FALSE);
			}
		} catch (RuntimeException e) {
			result.setSystemError();
			logger.error(e.getMessage(), e);
		}

		return result;
	}
}
