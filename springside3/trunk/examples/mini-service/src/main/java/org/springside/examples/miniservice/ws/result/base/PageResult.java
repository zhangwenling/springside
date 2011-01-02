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

	private int pageNo;
	private int pageSize;
	private long totalItems;

	public PageResult() {
	}

	public PageResult(int pageNo, int pageSize, long totalItems) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalItems = totalItems;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int page) {
		this.pageNo = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
}
