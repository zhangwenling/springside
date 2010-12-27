package org.springside.examples.miniservice.ws.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.WsConstants;
import org.springside.examples.miniservice.ws.dto.UserDTO;

/**
 * 包含UserListResult
 * 
 * @author calvin
 */
@XmlType(name = "UserListResult", namespace = WsConstants.NS)
public class UserListResult extends PageResult {

	private List<UserDTO> userList;

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}
