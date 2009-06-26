package org.springside.examples.showcase.common.service;

import java.util.List;

import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.log4j.Log4JStopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.email.MimeMailService;
import org.springside.examples.showcase.email.SimpleMailService;
import org.springside.examples.showcase.jmx.server.ServerConfig;
import org.springside.modules.orm.hibernate.EntityManager;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends EntityManager<User, Long> {
	@Autowired
	private UserDao userDao;
	@Autowired(required = false)
	private ServerConfig serverConfig; //系统配置
	@Autowired(required = false)
	private SimpleMailService simpleMailService;//邮件发送
	@Autowired(required = false)
	private MimeMailService mimeMailService;//邮件发送

	@Override
	protected UserDao getEntityDao() {
		return userDao;
	}

	/**
	 * 重载函数,在保存用户时,发送通知邮件.
	 */
	@Override
	public void save(User user) {

		super.save(user);

		sendNotifyMail(user);
	}

	/**
	 * 重载函数，调用预加载用户角色的Dao函数.
	 */
	//Perf4j监控性能
	@Profiled
	@Override
	public List<User> getAll() {
		return userDao.getAllUserWithRoleByHqlDistinctBySet();
	}

	/**
	 * 获取当前用户数量.
	 */
	public long getUserCount() {
		return userDao.findLong(UserDao.COUNT_USER);
	}

	/**
	 * 批量修改用户状态.
	 */
	public void disableUsers(List<Long> ids) {
		userDao.disableUsers(ids);
	}

	private void sendNotifyMail(User user) {
		if (serverConfig != null && serverConfig.isNotificationMailEnabled()) {
			//Perf4j监控性能
			StopWatch stopWatch = new Log4JStopWatch();
			try {
				if (simpleMailService != null) {
					simpleMailService.sendNotificationMail(user.getName());
				}

				if (mimeMailService != null) {
					mimeMailService.sendNotificationMail(user.getName());
				}
				stopWatch.stop("sendMail.success");
			} catch (Exception e) {
				logger.error("邮件发送失败", e);
				stopWatch.stop("sendMail.fail");
			}
		}
	}
}
