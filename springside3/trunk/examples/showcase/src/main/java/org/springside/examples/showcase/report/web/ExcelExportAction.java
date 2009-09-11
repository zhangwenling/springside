package org.springside.examples.showcase.report.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Namespace;
import org.springside.examples.showcase.report.data.DummyDataFetcher;
import org.springside.examples.showcase.report.data.DummyDataFetcher.TemperatureAnomaly;
import org.springside.modules.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 基于POI导出Excel文件的Action.
 * 
 * @author calvin
 */
@Namespace("/report")
public class ExcelExportAction extends ActionSupport {

	private static final long serialVersionUID = 1321167727728057491L;

	/**
	 * 生成Excel格式的内容.
	 */
	@Override
	public String execute() throws Exception {
		TemperatureAnomaly[] temperatureAnomalyArray = DummyDataFetcher.getDummyData();
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet();

		generateHeader(wb, s);
		generateContent(wb, s, temperatureAnomalyArray);

		HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType("application/vnd.ms-excel");
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		return null;
	}

	private void generateHeader(Workbook wb, Sheet s) {

		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 10);
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle cs = wb.createCellStyle();
		cs.setFont(f);

		Row r = s.createRow(0);

		Cell c1 = r.createCell(0);
		c1.setCellStyle(cs);
		c1.setCellValue("Year");

		Cell c2 = r.createCell(1);
		c2.setCellStyle(cs);
		c2.setCellValue("Anomaly");

		Cell c3 = r.createCell(2);
		c3.setCellStyle(cs);
		c3.setCellValue("Smoothed");

	}

	private void generateContent(Workbook wb, Sheet s, TemperatureAnomaly[] temperatureAnomalys) {

		DataFormat df = wb.createDataFormat();

		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 10);

		CellStyle dateStyle = wb.createCellStyle();
		dateStyle.setFont(f);
		dateStyle.setDataFormat(df.getFormat("yyyy"));

		CellStyle numberStyle = wb.createCellStyle();
		numberStyle.setFont(f);
		numberStyle.setDataFormat(df.getFormat("#,##0.000"));

		for (int i = 0; i < temperatureAnomalys.length; i++) {
			TemperatureAnomaly temperatureAnomaly = temperatureAnomalys[i];
			Row r = s.createRow(i + 1);
			Cell c1 = r.createCell(0);

			Calendar calendar = Calendar.getInstance();
			calendar.set(temperatureAnomaly.getYear(), 0, 1);

			c1.setCellValue(calendar);
			c1.setCellStyle(dateStyle);

			Cell c2 = r.createCell(1);
			c2.setCellValue(temperatureAnomaly.getAnomaly());
			c2.setCellStyle(numberStyle);

			Cell c3 = r.createCell(2);
			c3.setCellValue(temperatureAnomaly.getSmoothed());
			c3.setCellStyle(numberStyle);
		}

	}
}
