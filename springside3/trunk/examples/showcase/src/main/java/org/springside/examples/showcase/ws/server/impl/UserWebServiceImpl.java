package org.springside.examples.showcase.ws.server.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.examples.showcase.ws.server.api.UserWebService;
import org.springside.examples.showcase.ws.server.api.WsConstants;
import org.springside.examples.showcase.ws.server.api.dto.UserDTO;
import org.springside.examples.showcase.ws.server.api.result.GetAllUserResult;

import com.google.common.collect.Lists;

/**
 * WebService实现类.
 * 
 * @author sky
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称,endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "org.springside.examples.showcase.ws.server.api.UserWebService", targetNamespace = WsConstants.NS)
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

			List<UserDTO> userDTOList = Lists.newArrayList();
			for (User userEntity : userList) {
				userDTOList.add(dozer.map(userEntity, UserDTO.class));
			}
			result.setUserList(userDTOList);
			logger.info("transfer {} user sucessful.", userDTOList.size());
		} catch (RuntimeException e) {
			result.setSystemError();
			logger.error(e.getMessage(), e);
		}
		return result;
	}
}
