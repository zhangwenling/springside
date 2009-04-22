package org.springside.examples.miniweb.service.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.miniweb.entity.security.Resource;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.springside.modules.orm.hibernate.SimpleHibernateDao;
import org.springside.modules.security.springsecurity.ResourceDetailService;

/**
 * 从数据库查询URL--授权定义的RequestMapService实现类.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class ResourceDetailServiceImpl implements ResourceDetailService {

	private SimpleHibernateDao<Resource, Long> resourceDao;

	@Autowired
	public void init(final SessionFactory sessionFactory) {
		resourceDao = new HibernateDao<Resource, Long>(sessionFactory, Resource.class);
	}

	/**
	 * @see ResourceDetailService#getRequestMap()
	 */
	public LinkedHashMap<String, String> getRequestMap() throws Exception {
		List<Resource> resourceList = resourceDao.find(Resource.QUERY_BY_URL_TYPE, Resource.URL_TYPE);
		LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>();
		for (Resource resource : resourceList) {
			requestMap.put(resource.getValue(), resource.getAuthNames());
		}
		return requestMap;
	}

}
