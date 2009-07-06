package org.springside.examples.showcase.orm.hibernate;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

/**
 * 在自动为entity添加审计信息的Hibernate EventListener.
 * 
 * 在hibernate执行saveOrUpdate()时,自动为AuditableEntity的子类添加审计信息.
 * 
 * @author calvin
 */
@SuppressWarnings("serial")
public class AuditListener implements SaveOrUpdateEventListener {

	public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
		Object object = event.getObject();
		if (object instanceof AuditableEntity) {
			AuditableEntity entity = (AuditableEntity) object;

			if (entity.getId() == null) {
				entity.setCreateTime(new Date());
				entity.setCreateBy(SpringSecurityUtils.getCurrentUserName());
			} else {
				entity.setLastModifyTime(new Date());
				entity.setLastModifyBy(SpringSecurityUtils.getCurrentUserName());
			}
		}
	}
}
