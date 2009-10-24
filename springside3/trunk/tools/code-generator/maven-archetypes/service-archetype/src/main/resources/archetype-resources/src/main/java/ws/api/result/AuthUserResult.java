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
@XmlType(name = "AuthUserResult", namespace = WsConstants.NS)
public class AuthUserResult extends WSResult {

	private boolean valid;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
