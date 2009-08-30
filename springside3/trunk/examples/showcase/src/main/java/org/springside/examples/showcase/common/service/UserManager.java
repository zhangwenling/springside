package org.springside.examples.showcase.common.service;

import java.util.List;

import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.log4j.Log4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.email.MimeMailService;
import org.springside.examples.showcase.email.SimpleMailService;
import org.springside.examples.showcase.jmx.server.ServerConfig;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager {
	private static Logger logger = LoggerFactory.getLogger(UserManager.class);

	@Autowired
	private UserDao userDao;
	@Autowired(required = false)
	private ServerConfig serverConfig; //系统配置
	@Autowired(required = false)
	private SimpleMailService simpleMailService;//邮件发送
	@Autowired(required = false)
	private MimeMailService mimeMailService;//邮件发送

	public User getUser(Long id) {
		return userDao.get(id);
	}

	/**
	 * 在保存用户时,发送通知邮件.
	 * 如果企图修改超级用户,取出当前操作员用户,打印其信息然后抛出异常.
	 */
	public void saveUser(User user) {
		if (user.getId() == 1) {
			logger.warn("操作员{}尝试修改超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能修改超级管理员用户");
		}

		PasswordEncoder encoder = new ShaPasswordEncoder();
		String shaPassword = encoder.encodePassword(user.getPlainPassword(), null);
		user.setShaPassword(shaPassword);

		userDao.save(user);

		sendNotifyMail(user);
	}

	/**
	 * 取得所有用户,预加载用户的角色.
	 */
	//Perf4j监控性能
	@Profiled
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		return userDao.getAllUserWithRoleByDistinctHql();
	}

	/**
	 * 获取当前用户数量.
	 */
	@Transactional(readOnly = true)
	public Long getUserCount() {
		return userDao.findUnique(UserDao.COUNT_USERS);
	}

	@Transactional(readOnly = true)
	public User getUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 批量修改用户状态.
	 */
	public void disableUsers(List<Long> ids) {
		userDao.disableUsers(ids);
	}

	/**
	 * 发送用户变更通知邮件.
	 */
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
