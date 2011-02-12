package org.springside.modules.unit.utils.template;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.springside.modules.utils.template.VelocityFileProcessor;

import com.google.common.collect.Maps;

public class VelocityFileProcessorTest {

	@Test
	public void renderTemplateFile() throws Exception {
		VelocityFileProcessor holder = new VelocityFileProcessor("classpath:/");

		Map<String, String> model = Maps.newHashMap();
		model.put("userName", "calvin");
		String result = holder.render("testTemplate.vm", "UTF-8", model);
		assertEquals("hello calvin", result);
	}
}
