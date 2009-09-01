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

/**
 * 主题.
 * 
 * @author calvin
 */
@Entity
//标识字段值
@DiscriminatorValue("Subject")
public class Subject extends Post {

	private Set<Reply> replys = new LinkedHashSet<Reply>();

	//与回帖的一对多关系,在删除主题时cascade删除回帖.
	@OneToMany(mappedBy = "subject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	//按时间排序回帖
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
