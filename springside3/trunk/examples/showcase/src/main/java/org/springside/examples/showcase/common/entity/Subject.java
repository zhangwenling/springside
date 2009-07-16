package org.springside.examples.showcase.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@DiscriminatorValue("Subject")
public class Subject extends Post {
	protected List<Reply> replys = new ArrayList<Reply>();

	@OneToMany(mappedBy = "subject", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@OrderBy(value = "id DESC")
	public List<Reply> getReplys() {
		return replys;
	}

	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}

	public void addReply(Reply reply) {
		reply.setSubject(this);
		getReplys().add(reply);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
