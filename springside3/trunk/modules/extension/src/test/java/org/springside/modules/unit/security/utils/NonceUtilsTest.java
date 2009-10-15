package org.springside.modules.unit.security.utils;

import org.junit.Test;
import org.springside.modules.security.utils.NonceUtils;

public class NonceUtilsTest {
	@Test
	public void timestamp() {
		System.out.println(NonceUtils.getCurrentDate());
		System.out.println(NonceUtils.getCurrentTimpestamp());
	}

}
