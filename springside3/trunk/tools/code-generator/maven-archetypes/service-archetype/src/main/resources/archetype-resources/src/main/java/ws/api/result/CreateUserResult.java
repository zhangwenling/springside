#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.api.result;

import javax.xml.bind.annotation.XmlType;

import ${package}.ws.api.WsConstants;

/**
 * CreateUser方法的返回结果.
 * 
 * @author calvin
 */
@XmlType(name = "CreateUserResult", namespace = WsConstants.NS)
public class CreateUserResult extends WSResult {

	private Long userId;

	/**
	 * 新建用户的ID.
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
