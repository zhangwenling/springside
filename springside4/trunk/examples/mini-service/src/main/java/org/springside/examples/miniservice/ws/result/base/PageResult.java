package org.springside.examples.miniservice.ws.result.base;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.WsConstants;

/**
 * 分页查询返回的基础Result
 * 
 * @author badqiu
 * 
 */
@XmlType(name = "PageResult", namespace = WsConstants.NS)
public class PageResult extends WSResult {

	private long totalItems;

	public PageResult() {
	}

	public PageResult(long totalItems) {
		this.totalItems = totalItems;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
}
