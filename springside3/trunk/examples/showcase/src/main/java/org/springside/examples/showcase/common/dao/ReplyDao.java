package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.Reply;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class ReplyDao extends HibernateDao<Reply, Long> {
	public Reply getDetail(Long id) {
		return findUnique("from Reply r fetch all properties where r.id=?", id);
	}
}
