package org.springside.examples.miniservice.ws.impl;

import java.util.List;

import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springside.examples.miniservice.WsConstants;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.service.account.AccountManager;
import org.springside.examples.miniservice.ws.AccountWebService;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.DepartmentResult;
import org.springside.examples.miniservice.ws.result.UserPageResult;
import org.springside.examples.miniservice.ws.result.base.IdResult;
import org.springside.examples.miniservice.ws.result.base.WSResult;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.Asserter;
import org.springside.modules.utils.mapping.DozerUtils;
import org.springside.modules.utils.validator.ValidatorHolder;

/**
 * WebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "AccountService", portName = "AccountServicePort", endpointInterface = "org.springside.examples.miniservice.ws.AccountWebService", targetNamespace = WsConstants.NS)
public class AccountWebServiceImpl implements AccountWebService {

	private static Logger logger = LoggerFactory.getLogger(AccountWebServiceImpl.class);

	private AccountManager accountManager;

	/**
	 * @see AccountWebService#getDepartmentDetail()
	 */
	public DepartmentResult getDepartmentDetail(Long id) {

		//获取部门
		try {
			Department entity = accountManager.getDepartmentDetail(id);
			Asserter.notNull(entity, "部门不存在(id:" + id + ")");
			DepartmentDTO dto = DozerUtils.map(entity, DepartmentDTO.class);
			return new DepartmentResult(dto);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new DepartmentResult().setError(WSResult.PARAMETER_ERROR, e.getMessage());
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return new DepartmentResult().setDefaultErrorResult();
		}
	}

	/**
	 * @see AccountWebService#searchUser()
	 */
	public UserPageResult searchUser(String loginName, String name, int pageNo, int pageSize) {

		//获取User列表并转换为UserDTO列表.
		try {
			Page<User> page = accountManager.searchUser(loginName, name, pageNo, pageSize);

			List<UserDTO> dtoList = DozerUtils.mapList(page.getResult(), UserDTO.class);

			return new UserPageResult(page.getPageNo(), page.getPageSize(), page.getTotalItems(), dtoList);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return new UserPageResult().setDefaultErrorResult();
		}
	}

	/**
	 * @see AccountWebService#createUser(UserDTO)
	 */
	public IdResult createUser(UserDTO user) {

		//保存用户
		try {
			User userEntity = DozerUtils.map(user, User.class);

			Long userId = accountManager.saveUser(userEntity);

			return new IdResult(userId);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new IdResult().setError(WSResult.PARAMETER_ERROR, e.getMessage());
		} catch (DataIntegrityViolationException e) {
			String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
			logger.error(message, e);
			return new IdResult().setError(WSResult.PARAMETER_ERROR, message);
		} catch (ConstraintViolationException e) {
			String message = ValidatorHolder.convertMessage(e, "\n");
			return new IdResult().setError(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return new IdResult().setDefaultErrorResult();
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
