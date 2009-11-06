package org.springside.examples.showcase.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.NotifyMessageProducer;
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
	private NotifyMessageProducer notifyProducer; //JMX消息发送

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

		sendNotifyMessage(user);
	}

	/**
	 * 取得所有用户,预加载用户的角色.
	 */
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		List<User> list = userDao.getAllUserWithRoleByDistinctHql();
		logger.trace("get {} user sucessful.", list.size());
		return list;
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
	 * 发送用户变更消息.
	 */
	private void sendNotifyMessage(User user) {
		if (serverConfig != null && serverConfig.isNotificationMailEnabled() && notifyProducer != null) {
			try {
				notifyProducer.sendQueue(user);
				notifyProducer.sendTopic(user);
			} catch (Exception e) {
				logger.error("消息发送失败", e);
			}
		}
	}
}
