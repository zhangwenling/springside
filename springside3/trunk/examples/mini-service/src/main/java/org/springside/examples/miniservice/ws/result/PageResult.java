package org.springside.examples.miniservice.ws.result;

import org.springside.modules.orm.Page;
import org.springside.modules.orm.Paginator;

/**
 * 分页查询返回的基础Result
 * 
 * @author badqiu
 * 
 */
public class PageResult extends WSResult {

	private int pageNo;
	private int pageSize;
	private long totalItems;

	public PageResult() {}
	
	public PageResult(Paginator paginator) {
		super();
		paginator(paginator);
	}
	
	public PageResult(int page, int pageSize, long totalItems) {
		super();
		this.pageNo = page;
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

	public PageResult paginator(Paginator paginator ) {
		this.pageNo = paginator.getPageNo();
		this.pageSize = paginator.getPageSize();
		this.totalItems = paginator.getTotalItems();
		return this;
	}
	
	public Paginator toPaginator() {
		return new Paginator(pageNo, pageSize, totalItems);
	}
}
