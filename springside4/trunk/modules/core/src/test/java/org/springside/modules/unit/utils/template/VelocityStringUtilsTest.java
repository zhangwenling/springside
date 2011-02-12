package org.springside.modules.unit.utils.template;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springside.modules.utils.template.VelocityStringUtils;

import com.google.common.collect.Maps;

public class VelocityStringUtilsTest {
	private String TEMPLATE = "hello $userName";
	private String ERROR_TEMPLATE = "hello $";

	@Test
	public void renderContent() {
		Map<String, String> model = Maps.newHashMap();
		model.put("userName", "calvin");
		String result = VelocityStringUtils.render(TEMPLATE, model);
		assertEquals("hello calvin", result);
	}

	@Test(expected = Exception.class)
	public void renderContentWithErrorTemplate() {
		Map<String, String> model = Maps.newHashMap();
		model.put("userName", "calvin");
		VelocityStringUtils.render(ERROR_TEMPLATE, model);
	}
}
