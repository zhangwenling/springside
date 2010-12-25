package org.springside.examples.miniservice.ws.result;

/**
 * 分页查询返回的基础Result
 * 
 * @author badqiu
 * 
 */
public class PageResult extends WSResult {

	private int page;
	private int pageSize;
	private long totalItems;

	public PageResult(int page, int pageSize, long totalItems) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.totalItems = totalItems;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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
