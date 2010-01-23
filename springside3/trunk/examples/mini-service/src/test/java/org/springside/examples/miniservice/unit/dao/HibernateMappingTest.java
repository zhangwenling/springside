package org.springside.examples.miniservice.unit.dao;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.examples.miniservice.unit.BaseTxTestCase;

/**
 * 简单测试所有Entity类的O/R Mapping.
 *  
 * @author calvin
 */
public class HibernateMappingTest extends BaseTxTestCase {
	private static Logger logger = LoggerFactory.getLogger(HibernateMappingTest.class);

	@Test
	@SuppressWarnings("unchecked")
	public void allClassMapping() throws Exception {
		Session session = sessionFactory.openSession();

		try {
			Map metadata = sessionFactory.getAllClassMetadata();
			for (Object o : metadata.values()) {
				EntityPersister persister = (EntityPersister) o;
				String className = persister.getEntityName();
				Query q = session.createQuery("from " + className + " c");
				q.iterate();
				logger.debug("ok: " + className);
			}
		} finally {
			session.close();
		}
	}
}
