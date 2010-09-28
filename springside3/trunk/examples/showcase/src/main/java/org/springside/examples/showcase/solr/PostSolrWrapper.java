package org.springside.examples.showcase.solr;

import java.util.Date;

import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.common.SolrInputDocument;
import org.springside.examples.showcase.common.entity.Post;
import org.springside.examples.showcase.common.entity.User;

public class PostSolrWrapper {

	private static DocumentObjectBinder binder = new DocumentObjectBinder();

	private Post post;

	public void fromEntity(Post post) {
		this.post = post;
	}

	public Post toEntity() {
		return post;
	}

	public SolrInputDocument toDocument() {
		return binder.toSolrInputDocument(this);
	}

	@Field
	public String getId() {
		return post.getId();
	}

	public void setId(String id) {
		post.setId(id);
	}

	@Field
	public String getTitle() {
		return post.getTitle();
	}

	public void setTitle(String title) {
		post.setTitle(title);
	}

	@Field
	public String getContent() {
		return post.getContent();
	}

	@Field
	public String getAuthor() {
		return post.getUser().getLoginName();
	}

	public void setAuthor(String author) {
		User user = new User();
		user.setLoginName(author);
		post.setUser(user);
	}

	@Field
	public Date getModifyTime() {
		return post.getModifyTime();
	}

	public void setModifyTime(Date modifyTime) {
		post.setModifyTime(modifyTime);
	}
}