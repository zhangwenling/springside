package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class SubjectDao extends HibernateDao<Subject, Long> {

	public Subject getDetail(Long id) {
		return findUnique("from Subject s fetch all properties where s.id=?", id);
	}

	public Subject getDetailWithReply(Long id) {

		return (Subject) distinct(
				createQuery(
						"from Subject s fetch all properties left join fetch s.replys fetch all properties where s.id=?",
						id))
						.uniqueResult();

	}
}
