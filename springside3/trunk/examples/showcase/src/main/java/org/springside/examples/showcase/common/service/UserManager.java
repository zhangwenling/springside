package org.springside.examples.showcase.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jmx.server.ServerConfig;
import org.springside.examples.showcase.jmx.server.ServerStatistics;
import org.springside.modules.orm.hibernate.DefaultEntityManager;

/**
 * 用户管理类.
 * 
 * 实现领域对象用户的所有业务管理函数.
 * 通过范型声明继承DefaultEntityManager,默认拥有CRUD管理函数及HibernateDao<User,Long> entityDao成员变量.
 * 使用Spring annotation定义事务管理.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends DefaultEntityManager<User, Long> {

	@Autowired
	private ServerConfig serverConfig; //系统配置
	@Autowired
	private ServerStatistics  serverStatistics;//系统统计

	/**
	 * 重载getAll函数,在载入用户列表时,根据系统配置统计查询次数.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> getAll() {
		if (serverConfig.isStatisticsEnabled()) {
			serverStatistics.incQueryAllUsersCount();
		}
		return entityDao.getAll();
	}
}
