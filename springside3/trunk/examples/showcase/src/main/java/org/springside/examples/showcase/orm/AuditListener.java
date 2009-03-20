package org.springside.examples.showcase.orm;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.SaveOrUpdateEventListener;
import org.springside.examples.showcase.common.entity.AuditableEntity;
import org.springside.modules.security.springsecurity.SpringSecurityUtils;

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
