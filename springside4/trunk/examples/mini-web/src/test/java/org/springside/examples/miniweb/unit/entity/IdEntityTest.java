package org.springside.examples.miniweb.unit.entity;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springside.examples.miniweb.entity.IdEntity;
import org.springside.examples.miniweb.entity.account.User;

import com.google.common.collect.Lists;

public class IdEntityTest {

	@Test
	public void mergeByCheckedIds() {
		User a = new User();
		a.setId(1L);

		User b = new User();
		b.setId(1L);

		List<User> srcList = Lists.newArrayList(a, b);
		List<Long> idList = Lists.newArrayList(1L, 3L);

		IdEntity.mergeByIds(srcList, idList, User.class);

		assertEquals(2, srcList.size());
		assertTrue(1L == srcList.get(0).getId());
		assertTrue(3L == srcList.get(1).getId());
	}

}
