package org.springside.examples.showcase.common.entity;

// Generated Jul 11, 2009 5:26:18 PM by Hibernate Tools 3.2.4.GA

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "POSTS")
public class Post extends IdEntity {

	private String type;
	private String title;
	private String content;
	private User user;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	//延时加载的Lob字段, 需要运行instrument任务进行bytecode enhancement
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
