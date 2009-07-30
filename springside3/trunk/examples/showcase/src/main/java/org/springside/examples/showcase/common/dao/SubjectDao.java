package org.springside.examples.showcase.common.dao;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class SubjectDao extends HibernateDao<Subject, Long> {

	public static final String QUERY_WITH_DETAIL_AND_REPLY = "from Subject s fetch all properties left join fetch s.replys fetch all properties where s.id=?";
	public static final String QUERY_WITH_DETAIL = "from Subject s fetch all properties where s.id=?";

	public Subject getDetail(Long id) {
		return findUnique(QUERY_WITH_DETAIL, id);
	}

	public Subject getDetailWithReply(Long id) {
		return (Subject) distinct(createQuery(QUERY_WITH_DETAIL_AND_REPLY, id)).uniqueResult();
	}

	public void initSubjectWithReply(Subject subject) {
		Hibernate.initialize(subject.getReplys());
	}
}
