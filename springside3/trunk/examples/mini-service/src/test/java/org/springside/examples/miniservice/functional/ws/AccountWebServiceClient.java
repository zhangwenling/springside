package org.springside.examples.miniservice.functional.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;
import org.springside.examples.miniservice.ws.AccountWebService;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.exception.WebServiceException;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetDepartmentDetailResult;
import org.springside.examples.miniservice.ws.result.SearchUserResult;
import org.springside.examples.miniservice.ws.result.WSResult;
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
		CreateUserResult result = accountWebService.createUser(user);
		if(WSResult.SUCCESS.equals(result.getCode())) {
			return result.getUserId();
		}else {
			throw new WebServiceException(result.getCode(),result.getMessage());
		}
	}

	/**
	 * 解包Result对象并转换为Exception
	 * @param user
	 * @return
	 */
	public DepartmentDTO getDepartmentDetail(Long id) {
		GetDepartmentDetailResult result =  accountWebService.getDepartmentDetail(id);
		if(WSResult.SUCCESS.equals(result.getCode())) {
			return result.getDepartment();
		}else {
			throw new WebServiceException(result.getCode(),result.getMessage()+" id:"+id);
		}
	}

	/**
	 * 解包Result对象并转换为Exception
	 * @param user
	 * @return
	 */
	public List<UserDTO> searchUser(String loginName, String name) {
		SearchUserResult result = accountWebService.searchUser(loginName, name);
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
