package org.springside.examples.showcase.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.email.MimeMailService;
import org.springside.examples.showcase.email.SimpleMailService;
import org.springside.examples.showcase.jmx.server.ServerConfig;
import org.springside.examples.showcase.jmx.server.ServerMonitor;
import org.springside.modules.orm.hibernate.DefaultEntityManager;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class UserManager extends DefaultEntityManager<User, Long> {

	@Autowired(required = false)
	private ServerConfig serverConfig; //系统配置
	@Autowired(required = false)
	private ServerMonitor serverMonitor;//系统统计
	
	@Autowired(required = false)
	private SimpleMailService simpleMailService;//邮件发送
	@Autowired(required = false)
	private MimeMailService mimeMailService;//邮件发送

	/**
	 * 重载函数,在载入用户列表时,根据系统配置统计查询次数.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> getAll() {
		if (isStatisticsEnabled()) {
			serverMonitor.getServerStatistics().incQueryUserCount();
		}
		return super.getAll();
	}

	/**
	 * 重载函数,在保存用户时,发送通知邮件.
	 */
	@Override
	public void save(User user) {
		if (isStatisticsEnabled()) {
			serverMonitor.getServerStatistics().incModifyUserCount();
		}
		super.save(user);

		if (simpleMailService != null) {
			simpleMailService.sendNotifyMail(user.getName());
		}

		if (mimeMailService != null) {
			mimeMailService.sendNotifyMail(user.getName());
		}
	}

	/**
	 * 重载函数,在删除用户时,根据系统配置统计查询次数.
	 */
	@Override
	public void delete(Long id) {
		if (isStatisticsEnabled()) {
			serverMonitor.getServerStatistics().incDeleteUserCount();
		}
		super.delete(id);
	}

	@Transactional(readOnly = true)
	public User loadByLoginName(String loginName) {
		return entityDao.findUniqueByProperty("loginName", loginName);
	}

	@Transactional(readOnly = true)
	public long getUsersCount() {
		return (Long) entityDao.findUnique("select count(u) from User u");
	}

	private boolean isStatisticsEnabled() {
		return (serverConfig != null && serverConfig.isStatisticsEnabled() && serverMonitor != null);
	}
}
