package org.springside.examples.miniservice.ws.dto;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springside.examples.miniservice.ws.WsConstants;

/**
 * Web Service传输User信息的DTO.
 * 
 * 只传输外部接口需要的属性.使用JAXB 2.0的annotation标注JAVA-XML映射,尽量使用默认约定.
 * 
 * @author calvin
 */
@XmlType(name = "User", namespace = WsConstants.NS)
public class UserDTO {

	private Long id;
	private String loginName;
	private String name;
	private String email;
	private DepartmentDTO department;

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		id = value;
	}

	@NotBlank
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String value) {
		loginName = value;
	}

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String value) {
		email = value;
	}

	public DepartmentDTO getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
