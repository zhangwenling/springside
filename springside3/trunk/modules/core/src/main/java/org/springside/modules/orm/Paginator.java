package org.springside.modules.orm;

import java.util.List;

import com.google.common.collect.Lists;

public class Paginator {

	/** 分页大小 */
	private int pageSize;
	/** 页数  */
	private int pageNo;
	/** 总记录数 */
	private long totalItems;

	public Paginator(int pageNo, int pageSize, long totalItems) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalItems = totalItems;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 * 用于Oracle.
	 */
	public int getStartRow() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置.
	 * 用于Oracle.
	 */
	public long getEndRow() {
		return Math.min(pageSize * pageNo, totalItems);
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 * 用于Mysql.
	 */
	public int getOffset() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * 计算页内大小，考虑最后一页的长度.
	 * 用于Mysql
	 */
	public long getLimit() {
		return getEndRow() - (pageSize * (pageNo - 1));
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 * 用于Hibernate.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * 是否最后一页.
	 */
	public boolean isLastPage() {
		return pageNo == getTotalPages();
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNextPage() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNextPage()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否第一页.
	 */
	public boolean isFirstPage() {
		return pageNo == 1;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPrePage() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPrePage()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (totalItems < 0) {
			return -1;
		}

		long count = totalItems / pageSize;
		if (totalItems % pageSize > 0) {
			count++;
		}
		return count;
	}

	public List<Long> getSlider(int count) {
		int avg = count / 2;
		long totalPage = getTotalPages();

		long startPageNumber = pageNo - avg;
		if (startPageNumber <= 0) {
			startPageNumber = 1;
		}

		long endPageNumber = startPageNumber + count - 1;
		if (endPageNumber > totalPage) {
			endPageNumber = totalPage;
		}

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = endPageNumber - count;
			if (startPageNumber <= 0) {
				startPageNumber = 1;
			}
		}

		List<Long> result = Lists.newArrayList();
		for (long i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Long(i));
		}
		return result;
	}

}
