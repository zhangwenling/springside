package org.springside.examples.miniservice.ws.impl;

import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.service.ServiceException;
import org.springside.examples.miniservice.service.account.AccountManager;
import org.springside.examples.miniservice.ws.UserWebService;
import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.AuthUserResult;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetAllDepartmentResult;
import org.springside.examples.miniservice.ws.result.GetDepartmentDetailResult;
import org.springside.examples.miniservice.ws.result.WSResult;

import com.google.common.collect.Lists;

/**
 * WebService服务端实现类.
 * 
 * 客户端实现见功能测试用例.
 * 
 * @author calvin
 */
//serviceName与portName属性指明WSDL中的名称, endpointInterface属性指向Interface定义类.
@WebService(serviceName = "UserService", portName = "UserServicePort", endpointInterface = "org.springside.examples.miniservice.ws.UserWebService", targetNamespace = WsConstants.NS)
public class UserWebServiceImpl implements UserWebService {

	private static Logger logger = LoggerFactory.getLogger(UserWebServiceImpl.class);

	private AccountManager accountManager;

	private DozerBeanMapper dozer;

	private Validator validator;

	/**
	 * @see UserWebService#getAllUser()
	 */
	@Override
	public GetAllDepartmentResult getAllDepartment() {

		GetAllDepartmentResult result = new GetAllDepartmentResult();

		//获取User列表并转换为UserDTO列表.
		try {
			List<Department> departmentEntityList = accountManager.getAllDepartment();
			List<DepartmentDTO> departmentDTOList = Lists.newArrayList();

			for (Department departmentEntity : departmentEntityList) {
				departmentDTOList.add(dozer.map(departmentEntity, DepartmentDTO.class));
			}
			result.setDepartmentList(departmentDTOList);
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#getUser()
	 */
	@Override
	public GetDepartmentDetailResult getDepartmentDetail(Long id) {
		GetDepartmentDetailResult result = new GetDepartmentDetailResult();

		//校验请求参数
		try {
			Assert.notNull(id, "id参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//获取用户
		try {
			Department entity = accountManager.getDepartmentDetail(id);

			DepartmentDTO dto = dozer.map(entity, DepartmentDTO.class);

			result.setDepartment(dto);

			return result;
		} catch (ServiceException e) {
			String message = "用户不存在(id:" + id + ")";
			logger.error(message, e);
			return result.buildResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#createUser(UserDTO)
	 */
	@Override
	public CreateUserResult createUser(UserDTO user) {
		CreateUserResult result = new CreateUserResult();

		//校验请求参数
		try {
			Assert.notNull(user, "用户参数为空");
			Assert.isNull(user.getId(), "新建用户ID参数必须为空");

			//校验User内容
			Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(user);
			if (!constraintViolations.isEmpty()) {
				ConstraintViolation<UserDTO> violation = constraintViolations.iterator().next();
				String message = violation.getPropertyPath() + " " + violation.getMessage();
				throw new IllegalArgumentException(message);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//保存用户
		try {
			User userEntity = dozer.map(user, User.class);
			accountManager.saveUser(userEntity);
			result.setUserId(userEntity.getId());
			return result;
		} catch (ServiceException e) {
			String message = "新建用户参数存在唯一性冲突(用户:" + user + ")";
			logger.error(message, e);
			return result.buildResult(WSResult.PARAMETER_ERROR, message);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	/**
	 * @see UserWebService#authUser(String, String)
	 */
	@Override
	public AuthUserResult authUser(String loginName, String password) {
		AuthUserResult result = new AuthUserResult();

		//校验请求参数
		try {
			Assert.hasText(loginName, "登录名参数为空");
			Assert.hasText(password, "密码参数为空");
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return result.buildResult(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		//认证
		try {
			if (accountManager.authenticate(loginName, password)) {
				result.setValid(true);
			} else {
				result.setValid(false);
			}
			return result;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return result.buildDefaultErrorResult();
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setDozer(DozerBeanMapper dozer) {
		this.dozer = dozer;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}
