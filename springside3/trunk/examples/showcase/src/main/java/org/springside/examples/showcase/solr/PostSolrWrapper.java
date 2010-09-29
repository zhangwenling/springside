package org.springside.examples.showcase.solr;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springside.examples.showcase.common.entity.Post;
import org.springside.examples.showcase.common.entity.User;

public class PostSolrWrapper {

	private Post post;

	public void setEntity(Post post) {
		this.post = post;
	}

	public Post getEntity() {
		return post;
	}

	public String getId() {
		return post.getId();
	}

	@Field
	public void setId(String id) {
		post.setId(id);
	}

	public String getTitle() {
		return post.getTitle();
	}

	@Field
	public void setTitle(String title) {
		post.setTitle(title);
	}

	public String getContent() {
		return post.getContent();
	}

	@Field
	public void setContent(String content) {
		post.setContent(content);
	}

	public String getAuthor() {
		return post.getUser().getLoginName();
	}

	@Field
	public void setAuthor(String author) {
		User user = new User();
		user.setLoginName(author);
		post.setUser(user);
	}

	public Date getModifyTime() {
		return post.getModifyTime();
	}

	@Field
	public void setModifyTime(Date modifyTime) {
		post.setModifyTime(modifyTime);
	}
}