package org.springside.modules.unit.orm;

import static org.junit.Assert.*;

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
		assertEquals(-1, page.getTotalPages());
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
		assertEquals(1, page.getStartRow());
		page.setPageNo(2);
		assertEquals(11, page.getStartRow());

	}

	@Test
	public void getTotalPages() {
		page.setPageSize(10);

		page.setTotalItems(1);
		assertEquals(1, page.getTotalPages());

		page.setTotalItems(10);
		assertEquals(1, page.getTotalPages());

		page.setTotalItems(11);
		assertEquals(2, page.getTotalPages());
	}

	@Test
	public void hasNextOrPre() {
		page.setPageSize(10);
		page.setPageNo(1);

		page.setTotalItems(9);
		assertEquals(false, page.isHasNextPage());

		page.setTotalItems(11);
		assertEquals(true, page.isHasNextPage());

		page.setPageNo(1);
		assertEquals(false, page.isHasPrePage());

		page.setPageNo(2);
		assertEquals(true, page.isHasPrePage());
	}

	@Test
	public void getNextOrPrePage() {
		page.setPageNo(1);
		assertEquals(1, page.getPrePage());

		page.setPageNo(2);
		assertEquals(1, page.getPrePage());

		page.setPageSize(10);
		page.setTotalItems(11);
		page.setPageNo(1);
		assertEquals(2, page.getNextPage());

		page.setPageNo(2);
		assertEquals(2, page.getNextPage());
	}
}
