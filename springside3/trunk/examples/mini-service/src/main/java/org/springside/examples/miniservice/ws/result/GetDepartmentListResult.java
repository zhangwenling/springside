package org.springside.examples.miniservice.ws.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;

/**
 * GetDepartmentList方法的返回结果.
 * 
 * @author calvin
 */
@XmlType(name = "GetDepartmentListResult", namespace = WsConstants.NS)
public class GetDepartmentListResult extends WSResult {

	private List<DepartmentDTO> departmentList;

	@XmlElementWrapper(name = "departmentList")
	@XmlElement(name = "department")
	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}
}
