package org.springside.examples.showcase.common.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.modules.orm.hibernate.HibernateDao;

/**
 * 主题对象的泛型DAO.
 * 
 * @author calvin
 */
@Repository
public class SubjectDao extends HibernateDao<Subject, Long> {

	public static final String QUERY_WITH_DETAIL = "from Subject s fetch all properties where s.id=?";
	public static final String QUERY_WITH_DETAIL_AND_REPLY = "from Subject s fetch all properties left join fetch s.replys fetch all properties where s.id=?";

	/**
	 * 获取帖子内容.
	 */
	public Subject getDetail(Long id) {
		return findUnique(QUERY_WITH_DETAIL, id);
	}

	/**
	 * 获取帖子内容及回复.
	 */
	public Subject getDetailWithReply(Long id) {
		Query query = createQuery(QUERY_WITH_DETAIL_AND_REPLY, id);
		return (Subject) distinct(query).uniqueResult();
	}

	/**
	 * 初始化帖子的内容及回复.
	 */
	public void initAllProperties(Subject subject) {
		Hibernate.initialize(subject.getReplyList());
		Hibernate.initialize(subject.getContent());
	}
}
