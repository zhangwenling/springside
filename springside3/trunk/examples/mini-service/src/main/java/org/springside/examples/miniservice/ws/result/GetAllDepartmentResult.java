package org.springside.examples.miniservice.ws.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;

/**
 * GetAllDepartment方法的返回结果.
 * 
 * @author calvin
 */
@XmlType(name = "GetAllDepartmentResult", namespace = WsConstants.NS)
public class GetAllDepartmentResult extends WSResult {

	private List<DepartmentDTO> departmentList;

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}
}
