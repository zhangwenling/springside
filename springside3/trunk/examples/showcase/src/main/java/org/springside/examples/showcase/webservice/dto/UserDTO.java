package org.springside.examples.showcase.webservice.dto;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springside.examples.showcase.webservice.WebServiceSupport;

/**
 * Web Service传输User信息的DTO.
 * 
 * 使用JAXB 2.0的annotation标注JAVA-XML映射,尽量使用默认约定.
 * 
 * @author calvin
 */
@XmlType(name = "User", namespace = WebServiceSupport.NS)
public class UserDTO {

	private Long id;
	private String loginName;
	private String name;
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long value) {
		id = value;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String value) {
		loginName = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String value) {
		email = value;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
