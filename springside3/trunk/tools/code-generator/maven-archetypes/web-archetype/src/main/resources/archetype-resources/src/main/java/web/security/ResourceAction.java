#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.security;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import ${package}.entity.security.Resource;
import ${package}.service.security.SecurityEntityManager;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 资源-权限列表.
 * 
 * @author calvin
 */
@Namespace("/security")
public class ResourceAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SecurityEntityManager securityEntityManager;

	private List<Resource> allResourceList;

	@Override
	public String execute() {
		allResourceList = securityEntityManager.getUrlResourceWithAuthorities();
		return SUCCESS;
	}

	public List<Resource> getAllResourceList() {
		return allResourceList;
	}
}
