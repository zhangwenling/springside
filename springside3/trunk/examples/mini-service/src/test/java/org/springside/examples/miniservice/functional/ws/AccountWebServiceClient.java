package org.springside.examples.miniservice.functional.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.ws.AccountWebService;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.exception.WebServiceException;
import org.springside.examples.miniservice.ws.result.DepartmentResult;
import org.springside.examples.miniservice.ws.result.IdResult;
import org.springside.examples.miniservice.ws.result.UserListResult;
import org.springside.examples.miniservice.ws.result.WSResult;
import org.springside.modules.utils.Asserter;
/**
 * 为WebService的一个Client,主要作用为
 * 1. 如果不是Success状态,将Result.code转换为 WebServiceException抛出,避免外部API还要判断方法是否调用成功
 * 2. 将Result的数据解包,如将SearchUserResult解包为List<UserDTO>并返回,避免外部API接触至Result对象
 * 
 * @author badqiu
 */
public class AccountWebServiceClient {
	private AccountWebService accountWebService;
	
	/**
	 * 解包Result对象并转换为Exception
	 * @param user
	 * @return
	 */
	public Long createUser(UserDTO user) {
		IdResult result = accountWebService.createUser(user);
		
		//演示使用更加有用的Asserter,assert失败将抛出指定异常
		Asserter.isTrue(WSResult.SUCCESS.equals(result.getCode()),new WebServiceException(result.getCode(),result.getMessage()));
		return result.getId();
	}

	/**
	 * 解包Result对象并转换为Exception
	 * @param user
	 * @return
	 */
	public DepartmentDTO getDepartmentDetail(Long id) {
		DepartmentResult result =  accountWebService.getDepartmentDetail(id);
		if(WSResult.SUCCESS.equals(result.getCode())) {
			return result.getDepartment();
		}else {
			//为我们的异常增加更加上下文id=?,以便查找是那个用户发生异常. 目的是使用"异常"代替日志的打印
			throw new WebServiceException(result.getCode(),result.getMessage()+" id:"+id);
		}
	}

	/**
	 * 解包Result对象并转换为Exception
	 * @param user
	 * @return
	 */
	public List<UserDTO> searchUser(String loginName, String name) {
		UserListResult result = accountWebService.searchUser(loginName, name);
		if(WSResult.SUCCESS.equals(result.getCode())) {
			return result.getUserList();
		}else {
			throw new WebServiceException(result.getCode(),result.getMessage());
		}
	}
	
	@Autowired
	public void setAccountWebService(AccountWebService accountWebService) {
		this.accountWebService = accountWebService;
	}
}
