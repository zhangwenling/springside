package org.springside.examples.showcase.unit.report;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.junit.Assert;
import org.junit.Test;
import org.springside.examples.showcase.report.web.ExcelExportAction;
import org.springside.modules.utils.ReflectionUtils;

public class ExcelExportActionTest extends Assert {

	@Test
	public void test() throws Exception {
		ExcelExportAction action = new ExcelExportAction();
		Workbook wb = (Workbook) ReflectionUtils.invokeMethod(action, "exportExcelWorkbook", null, null);

		CellReference cr = new CellReference("B3");
		Cell cell1970 = wb.getSheetAt(0).getRow(cr.getRow()).getCell(cr.getCol());
		assertEquals(-0.068, cell1970.getNumericCellValue(), 0.01);

		Cell cellTotal = wb.getSheetAt(0).getRow(32).getCell(1);
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		assertEquals(2.373, evaluator.evaluate(cellTotal).getNumberValue(), 0.01);

	}
}
