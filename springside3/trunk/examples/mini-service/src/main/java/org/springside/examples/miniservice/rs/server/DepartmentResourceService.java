package org.springside.examples.miniservice.rs.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.examples.miniservice.entity.account.Department;
import org.springside.examples.miniservice.rs.dto.DepartmentDTO;
import org.springside.examples.miniservice.service.account.AccountManager;

import com.google.common.collect.Lists;

/**
 * Department资源的REST服务.
 * 
 * @author calvin
 */
@Component
@Path("/departments")
public class DepartmentResourceService {

	private static final String CHARSET = ";charset=UTF-8";

	private static Logger logger = LoggerFactory.getLogger(DepartmentResourceService.class);

	private AccountManager accountManager;

	private DozerBeanMapper dozer;

	/**
	 * 获取所有部门.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public List<DepartmentDTO> getDepartmentList() {
		try {
			List<Department> entityList = accountManager.getDepartmentList();

			List<DepartmentDTO> dtoList = Lists.newArrayList();
			for (Department entity : entityList) {
				dtoList.add(dozer.map(entity, DepartmentDTO.class));
			}

			return dtoList;
		} catch (RuntimeException e) {
			throw JerseyServerUtils.buildException(logger, e);
		}
	}

	/**
	 * 获取部门详细信息.
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public DepartmentDTO getDepartmentDetail(@PathParam("id") Long id) {
		try {
			Department entity = accountManager.getDepartmentDetail(id);

			if (entity == null) {
				String message = "部门不存在(id:" + id + ")";
				throw JerseyServerUtils.buildException(logger, Status.NOT_FOUND, message);
			}

			DepartmentDTO dto = dozer.map(entity, DepartmentDTO.class);
			return dto;
		} catch (RuntimeException e) {
			throw JerseyServerUtils.buildException(logger, e);
		}
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setDozer(DozerBeanMapper dozer) {
		this.dozer = dozer;
	}
}
