package org.springside.modules.unit.orm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springside.modules.orm.Page;

public class PageTest {
	private Page<Object> page;

	@Before
	public void setUp() {
		page = new Page<Object>();
	}

	/**
	 * 检测Page的默认值契约.
	 */
	@Test
	public void defaultParameter() {
		assertEquals(1, page.getPageNo());
		assertEquals(-1, page.getPageSize());
		assertEquals(-1, page.getTotalItems());
		assertEquals(-1, page.getPageinator().getTotalPages());
		assertEquals(true, page.isAutoCount());

		page.setPageNo(-1);
		assertEquals(1, page.getPageNo());

		assertNull(page.getOrder());
		assertNull(page.getOrderBy());

		assertEquals(false, page.isOrderBySetted());
		page.setOrderBy("Id");
		assertEquals(false, page.isOrderBySetted());
		page.setOrder("ASC,desc");
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkInvalidOrderParameter() {
		page.setOrder("asc,abcd");
	}

	@Test
	public void getFirst() {
		page.setPageSize(10);

		page.setPageNo(1);
		assertEquals(1, page.getPageinator().getStartRow());
		page.setPageNo(2);
		assertEquals(11, page.getPageinator().getStartRow());

	}

	@Test
	public void getTotalPages() {
		page.setPageSize(10);

		page.setTotalItems(1);
		assertEquals(1, page.getPageinator().getTotalPages());

		page.setTotalItems(10);
		assertEquals(1, page.getPageinator().getTotalPages());

		page.setTotalItems(11);
		assertEquals(2, page.getPageinator().getTotalPages());
	}

	@Test
	public void hasNextOrPre() {
		page.setPageSize(10);
		page.setPageNo(1);

		page.setTotalItems(9);
		assertEquals(false, page.getPageinator().isHasNextPage());

		page.setTotalItems(11);
		assertEquals(true, page.getPageinator().isHasNextPage());

		page.setPageNo(1);
		assertEquals(false, page.getPageinator().isHasPrePage());

		page.setPageNo(2);
		assertEquals(true, page.getPageinator().isHasPrePage());
	}

	@Test
	public void getNextOrPrePage() {
		page.setPageNo(1);
		assertEquals(1, page.getPageinator().getPrePage());

		page.setPageNo(2);
		assertEquals(1, page.getPageinator().getPrePage());

		page.setPageSize(10);
		page.setTotalItems(11);
		page.setPageNo(1);
		assertEquals(2, page.getPageinator().getNextPage());

		page.setPageNo(2);
		assertEquals(2, page.getPageinator().getNextPage());
	}

	@Test
	public void setAllParameterInOneLine() {
		page.pageNo(2).pageSize(10).orderBy("abc").order(Page.ASC).autoCount(true);

		assertEquals(2, page.getPageNo());
		assertEquals(10, page.getPageSize());
		assertEquals("abc", page.getOrderBy());
		assertEquals(Page.ASC, page.getOrder());
		assertEquals(true, page.isAutoCount());
	}
}
