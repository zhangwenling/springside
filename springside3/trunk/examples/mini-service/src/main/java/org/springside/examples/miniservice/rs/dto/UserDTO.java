package org.springside.examples.miniservice.rs.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Web Service传输User信息的DTO.
 * 
 * @author calvin
 */
@XmlRootElement(name = "User")
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

	@NotBlank(message = "登录名不能为空")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String value) {
		loginName = value;
	}

	@NotBlank(message = "姓名不能为空")
	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	@Email(message = "邮件地址格式不正确")
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
