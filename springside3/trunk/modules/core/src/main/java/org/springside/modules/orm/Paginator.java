package org.springside.modules.orm;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 封装所有分页逻辑.
 * 
 * @author badqiu
 * @author calvin
 */
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
	 * 获得分页大小
	 */
	public int getPageSize() {
		return pageSize;
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
	 * 根据pageSize与totalItems计算总页数, 默认值为-1.
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

	/**
	 * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
	 * @param count 居然计算的列表大小
	 * @return pageNo列表 
	 */
	public List<Long> getSlider(int count) {
		int halfSize = count / 2;
		long totalPage = getTotalPages();

		long startPageNumber = Math.max(pageNo - halfSize, 1);
		long endPageNumber = Math.min(startPageNumber + count - 1, totalPage);

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = Math.max(endPageNumber - count, 1);
		}

		List<Long> result = Lists.newArrayList();
		for (long i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Long(i));
		}
		return result;
	}

}
