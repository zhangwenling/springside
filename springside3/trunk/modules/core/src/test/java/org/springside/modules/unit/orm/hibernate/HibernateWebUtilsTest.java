package org.springside.modules.unit.orm.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.hibernate.HibernateWebUtils;

public class HibernateWebUtilsTest extends Assert {

	@Test
	public void mergeByCheckedIds() {
		List<TestBean> srcList = new ArrayList<TestBean>();
		srcList.add(new TestBean(1));
		srcList.add(new TestBean(2));

		List<Integer> idList = new ArrayList<Integer>();
		idList.add(1);
		idList.add(3);

		HibernateWebUtils.cleanCheckedCollections(srcList, idList);

		assertEquals(2, srcList.size());
		assertEquals(1, srcList.get(0).getId());
		assertEquals(3, srcList.get(1).getId());

	}

	@Test
	public void buildPropertyFilters() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("filter_EQ_loginName", "abcd");
		request.setParameter("filter_LIKE_name_OR_email", "efg");

		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(request);

		assertEquals(2, filters.size());

		PropertyFilter filter1 = filters.get(0);
		assertEquals(PropertyFilter.MatchType.EQ, filter1.getMatchType());
		assertEquals("loginName", filter1.getPropertyName());
		assertEquals("abcd", filter1.getValue());

		PropertyFilter filter2 = filters.get(1);
		assertEquals(PropertyFilter.MatchType.LIKE, filter2.getMatchType());
		assertEquals("name_OR_email", filter2.getPropertyName());
		assertEquals("efg", filter2.getValue());
	}

	public static class TestBean {
		private int id;

		public TestBean() {
		}

		public TestBean(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}

}
