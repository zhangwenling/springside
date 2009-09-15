package org.springside.examples.showcase.unit.report;

import org.junit.Test;
import org.springside.examples.showcase.report.web.ExcelExportAction;
import org.springside.modules.utils.ReflectionUtils;

public class ExcelExportActionTest {

	/**
	 * 测试生成Excel时无异常产生.
	 */
	@Test
	public void export() throws Exception {
		ExcelExportAction action = new ExcelExportAction();
		ReflectionUtils.invokeMethod(action, "exportExcelWorkbook", null, null);
	}
}
