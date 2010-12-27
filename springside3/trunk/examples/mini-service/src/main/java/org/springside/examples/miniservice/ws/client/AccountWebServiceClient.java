package org.springside.examples.miniservice.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniservice.ws.AccountWebService;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.exception.WebServiceException;
import org.springside.examples.miniservice.ws.result.DepartmentResult;
import org.springside.examples.miniservice.ws.result.UserPageResult;
import org.springside.examples.miniservice.ws.result.base.IdResult;
import org.springside.examples.miniservice.ws.result.base.WSResult;
import org.springside.modules.orm.Page;
import org.springside.modules.utils.Asserter;
/**
 * 为WebService的一个Client,主要作用为
 * 1. 如果不是Success状态,将Result.code转换为 WebServiceException抛出,避免外部API还要判断方法是否调用成功
 * 2. 将Result的数据解包,如将UserPageResult解包为Page<UserDTO>并返回,避免外部API接触至Result对象
 * 
 * 还有另外一种转换为Exception的方式是使用拦截器: 拦截所有返回 WSResult的方法,如果code 不是success,则转换为WebServiceException然后抛出
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
	public Page<UserDTO> searchUser(String loginName, String name,int pageNo,int pageSize) {
		UserPageResult result = accountWebService.searchUser(loginName, name,pageNo,pageSize);
		if(WSResult.SUCCESS.equals(result.getCode())) {
			return new Page<UserDTO>(result.getUserList(),result.toPaginator());
		}else {
			throw new WebServiceException(result.getCode(),result.getMessage());
		}
	}

	@Autowired
	public void setAccountWebService(AccountWebService accountWebService) {
		this.accountWebService = accountWebService;
	}
}
