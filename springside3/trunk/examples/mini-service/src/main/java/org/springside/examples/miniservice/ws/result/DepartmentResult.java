package org.springside.examples.miniservice.ws.result;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.WsConstants;
import org.springside.examples.miniservice.ws.dto.DepartmentDTO;

/**
 * GetDepartment方法的返回结果.
 * 
 * @author calvin
 * @author badqiu
 */
@XmlType(name = "DepartmentResult", namespace = WsConstants.NS)
public class DepartmentResult extends WSResult {

	private DepartmentDTO department;

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}
}
