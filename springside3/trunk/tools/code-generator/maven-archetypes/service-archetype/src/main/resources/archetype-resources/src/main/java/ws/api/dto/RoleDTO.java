#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ws.api.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import ${package}.ws.api.Constants;

/**
 * Web Service传输Role信息的DTO.
 *
 * @author calvin
 */
@XmlType(name = "Role", namespace = Constants.NS)
public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1968746070218905430L;

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
