package org.springside.examples.showcase.common.entity;

import java.util.LinkedHashSet;
import java.util.Set;

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

	protected Set<Reply> replys = new LinkedHashSet<Reply>();

	@OneToMany(mappedBy = "subject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@OrderBy(value = "modifyTime DESC")
	public Set<Reply> getReplys() {
		return replys;
	}

	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
