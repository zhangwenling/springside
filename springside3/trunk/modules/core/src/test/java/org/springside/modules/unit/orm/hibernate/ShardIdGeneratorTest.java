package org.springside.modules.unit.orm.hibernate;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.orm.hibernate.ShardIdGenerator;

public class ShardIdGeneratorTest extends Assert {

	@Test
	public void generateStringId() {
		ShardIdGenerator generator = new ShardIdGenerator();
		String id = (String) generator.generate(null, null);
		System.out.println(id);
		assertTrue(id.length() == 15);

	}
}
