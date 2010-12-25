package org.springside.examples.miniservice.unit.ws;

import static org.junit.Assert.assertEquals;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.dozer.DozerBeanMapper;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.service.account.AccountManager;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.impl.AccountWebServiceImpl;
import org.springside.examples.miniservice.ws.result.DepartmentResult;
import org.springside.examples.miniservice.ws.result.WSResult;
import org.springside.modules.utils.validator.ValidatorHolder;

/**
 * Account WebService的单元测试用例.
 * 
 * 使用EasyMock对AccountManager进行模拟.
 * 
 * @author calvin
 */
public class AccountWebServiceTest {

	private IMocksControl control = EasyMock.createControl();

	private AccountWebServiceImpl accountWebService;
	private AccountManager mockAccountManager;

	@Before
	public void setUp() {
		accountWebService = new AccountWebServiceImpl();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		new ValidatorHolder().setValidator(factory.getValidator());

		//创建mock对象
		mockAccountManager = control.createMock(AccountManager.class);
		accountWebService.setAccountManager(mockAccountManager);
	}

	@After
	public void tearDown() {
		//确认的脚本都已执行
		control.verify();
	}

	/**
	 * 测试dozer正确映射.
	 */
	@Test
	public void dozerBinding() {
		Department department = AccountData.getRandomDepartment();
		EasyMock.expect(mockAccountManager.getDepartmentDetail(1L)).andReturn(department);
		control.replay();

		DepartmentResult result = accountWebService.getDepartmentDetail(1L);
		assertEquals(WSResult.SUCCESS, result.getCode());
		DepartmentDTO dto = result.getDepartment();
		assertEquals(department.getName(), dto.getName());
		assertEquals(department.getUserList().get(0).getName(), dto.getUserList().get(0).getName());
	}

	/**
	 * 测试参数校验.
	 */
	@Test
	public void validateParamter() {
		control.replay();
		WSResult result = accountWebService.createUser(null);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());

		User testUser = AccountData.getRandomUser();
		UserDTO userDTOWithoutLoginName = new DozerBeanMapper().map(testUser, UserDTO.class);
		userDTOWithoutLoginName.setLoginName(null);
		result = accountWebService.createUser(userDTOWithoutLoginName);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());

		testUser = AccountData.getRandomUser();
		UserDTO userDTOWitWrongEmail = new DozerBeanMapper().map(testUser, UserDTO.class);
		userDTOWitWrongEmail.setEmail("abc");
		result = accountWebService.createUser(userDTOWitWrongEmail);
		assertEquals(WSResult.PARAMETER_ERROR, result.getCode());
		System.out.println(result.getMessage());
	}

	/**
	 * 测试系统内部抛出异常时的处理.
	 */
	@Test
	public void handleException() {
		EasyMock.expect(mockAccountManager.getDepartmentDetail(1L)).andThrow(
				new RuntimeException("Expected exception."));
		control.replay();

		DepartmentResult result = accountWebService.getDepartmentDetail(1L);
		assertEquals(WSResult.SYSTEM_ERROR, result.getCode());
		assertEquals(WSResult.SYSTEM_ERROR_MESSAGE, result.getMessage());
	}
}
