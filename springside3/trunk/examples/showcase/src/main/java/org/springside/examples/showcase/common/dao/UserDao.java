package org.springside.examples.showcase.common.dao;

import org.springframework.stereotype.Repository;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.orm.hibernate.HibernateDao;

@Repository
public class UserDao extends HibernateDao<User, Long> {
}
