#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.result;

import javax.xml.bind.annotation.XmlType;

import ${package}.ws.WsConstants;
import ${package}.ws.dto.UserDTO;

/**
 * GetUser方法的返回结果.
 * 
 * @author calvin
 */
@XmlType(name = "GetUserResult", namespace = WsConstants.NS)
public class GetUserResult extends WSResult {

	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
