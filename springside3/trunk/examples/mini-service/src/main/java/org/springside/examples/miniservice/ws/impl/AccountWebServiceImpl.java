package org.springside.examples.miniservice.ws.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.validation.ConstraintViolation;

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
import org.springside.examples.miniservice.ws.result.IdResult;
import org.springside.examples.miniservice.ws.result.UserPageResult;
import org.springside.examples.miniservice.ws.result.WSResult;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.Asserter;
import org.springside.modules.utils.mapping.DozerUtils;
import org.springside.modules.utils.validator.ValidatorHolder;

import com.google.common.collect.Maps;

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
		DepartmentResult result = new DepartmentResult();

		//校验请求参数
		try {
			Asserter.notNull(id, "id参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.setResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//获取部门
		try {
			Department entity = accountManager.getDepartmentDetail(id);

			if (entity == null) {
				String message = "部门不存在(id:" + id + ")";
				logger.error(message);
				return result.setResult(WSResult.PARAMETER_ERROR, message);
			}

			DepartmentDTO dto = DozerUtils.map(entity, DepartmentDTO.class);
			result.setDepartment(dto);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.setDefaultErrorResult();
		}
	}

	/**
	 * @see AccountWebService#searchUser()
	 */
	public UserPageResult searchUser(String loginName, String name,int pageNo,int pageSize) {

		UserPageResult result = new UserPageResult();

		//获取User列表并转换为UserDTO列表.
		try {
			Map<String, String> parameters = Maps.newHashMap();
			parameters.put("loginName", loginName);
			parameters.put("name", name);

			Page<User> page = accountManager.searchUser(parameters,pageNo,pageSize);

			List<UserDTO> dtoList = DozerUtils.mapList(page, UserDTO.class);
			result.setUserList(dtoList);
			result.paginator(page.getPaginator());

			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.setDefaultErrorResult();
		}
	}

	/**
	 * @see AccountWebService#createUser(UserDTO)
	 */
	public IdResult createUser(UserDTO user) {
		IdResult result = new IdResult();

		//Hibernate Validator校验请求参数
		try {
			Asserter.notNull(user, "用户参数为空");
			Asserter.isNull(user.getId(), "新建用户ID参数必须为空");

			Set<ConstraintViolation<UserDTO>> constraintViolations = ValidatorHolder.validate(user);
			if (!constraintViolations.isEmpty()) {
				String message = ValidatorHolder.convertMessage(constraintViolations, "\n");
				throw new IllegalArgumentException(message);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.setResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//保存用户
		try {
			User userEntity = DozerUtils.map(user, User.class);

			Long userId = accountManager.saveUser(userEntity);

			result.setId(userId);
			return result;
		} catch (DataIntegrityViolationException e) {
			String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
			logger.error(message, e);
			return result.setResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.setDefaultErrorResult();
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

}
