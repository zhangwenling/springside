package org.springside.examples.showcase.common.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForceDiscriminator;

@Entity
@Table(name = "POSTS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ForceDiscriminator
public abstract class Post extends IdEntity {
	protected String title;
	protected String content;
	protected User user;
	protected Date modifyTime;

	//延时加载的Lob字段, 需要运行instrument任务进行bytecode enhancement
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
