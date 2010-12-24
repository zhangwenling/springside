package org.springside.examples.showcase.webservice.ws.server.impl;

import java.util.List;

import javax.jws.WebService;

import org.hibernate.ObjectNotFoundException;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.AccountManager;
import org.springside.examples.showcase.webservice.WsConstants;
import org.springside.examples.showcase.webservice.ws.server.UserWebService;
import org.springside.examples.showcase.webservice.ws.server.dto.UserDTO;
import org.springside.examples.showcase.webservice.ws.server.result.GetAllUserResult;
import org.springside.examples.showcase.webservice.ws.server.result.GetUserResult;
import org.springside.examples.showcase.webservice.ws.server.result.WSResult;
import org.springside.modules.utils.Asserter;
import org.springside.modules.utils.mapping.DozerUtils;

/**
 * UserWebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "org.springside.examples.showcase.webservice.ws.server.UserWebService", targetNamespace = WsConstants.NS)
public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = LoggerFactory.getLogger(UserWebServiceImpl.class);

	@Autowired
	private AccountManager accountManager;

	/**
	 * @see UserWebService#getAllUser()
	 */
	@Override
	public GetAllUserResult getAllUser() {
		GetAllUserResult result = new GetAllUserResult();

		//获取User列表并转换为UserDTO列表.
		try {
			List<User> userEntityList = accountManager.getAllUserWithRole();
			List<UserDTO> userDTOList = DozerUtils.mapList(userEntityList, UserDTO.class);
			result.setUserList(userDTOList);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.setDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#getUser()
	 */
	@Override
	//SpringSecurity 控制的方法
	@Secured({ "ROLE_Admin" })
	public GetUserResult getUser(Long id) {
		GetUserResult result = new GetUserResult();

		StopWatch totalStopWatch = new Slf4JStopWatch();
		//校验请求参数
		try {
			Asserter.notNull(id, "id参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.setResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//获取用户
		try {

			StopWatch dbStopWatch = new Slf4JStopWatch("GetUser.fetchDB");
			User entity = accountManager.getInitializedUser(id);
			dbStopWatch.stop();

			UserDTO dto = DozerUtils.map(entity, UserDTO.class);

			result.setUser(dto);

			totalStopWatch.stop("GerUser.total.success");

			return result;
		} catch (ObjectNotFoundException e) {
			String message = "用户不存在(id:" + id + ")";
			logger.error(message, e);
			totalStopWatch.stop("GerUser.total.failure");
			return result.setResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			totalStopWatch.stop("GerUser.total.failure");
			return result.setDefaultErrorResult();
		}
	}
}
