package org.springside.examples.miniweb.integration.dao.security;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springside.examples.miniweb.dao.security.ResourceDao;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * ResourceDao的集成测试用例,测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
public class ResourceDaoTest extends SpringTxTestCase {
	@Inject
	private ResourceDao entityDao;

	@Test
	public void getUrlResourceWithAuthorities() {
		List<Resource> resourceList = entityDao.getUrlResourceWithAuthorities();
		//校验资源的总数、排序及其授权已初始化
		assertEquals(countRowsInTable("SS_RESOURCE"), resourceList.size());
		Resource resource = resourceList.get(0);
		assertTrue(resource.getPosition() == 1.0);
		evict(resource);
		assertTrue(resource.getAuthorityList().size() > 0);
	}
}