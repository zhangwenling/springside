package org.springside.examples.showcase.report.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Namespace;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springside.examples.showcase.report.data.DummyDataFetcher;
import org.springside.examples.showcase.report.data.DummyDataFetcher.TemperatureAnomaly;
import org.springside.modules.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionSupport;

/**
 * FlashChart演示生成Amcharts所需 CVS/XML格式数据的Action.
 * 
 * @author calvin
 */
//因为没有按Convention Plugin默认的Pacakge命名规则, 因此用annotation重新指定Namespace.
@Namespace("/report/flashchart")
@SuppressWarnings("serial")
public class ChartDataAction extends ActionSupport {

	/**
	 * 生成CSV格式的内容.
	 * 示例:
	 * 1970;-0.068;-0.108
	 */
	public String report1() {
		TemperatureAnomaly[] temperatureAnomalyArray = DummyDataFetcher.getDummyData();
		StringBuilder output = new StringBuilder();
		for (TemperatureAnomaly data : temperatureAnomalyArray) {
			output.append(data.getYear()).append(";").append(data.getAnomaly()).append(";").append(data.getSmoothed())
					.append("\n");
		}
		Struts2Utils.render("text/csv", output.toString());
		return null;
	}

	/**
	 * 生成XML格式内容.
	 * 示例:
	 * <chart>
	 * 	<series>
	 * 		<value xid="1">1950</value>
	 * 	</series>
	 * 	<graphs>
	 * 		<graph gid="1">
	 * 			<value xid="1" color="#318DBD" url="/showcase/report/flashchart/report2-drilldown.action?id=100">-0.307</value>
	 * 		</graph>
	 * 		<graph gid="2">
	 * 			<value xid="1">-0.171</value>
	 * 		</graph>
	 * 	</graphs>
	 * </chart>
	 */
	public String report2() throws IOException {
		TemperatureAnomaly[] temperatureAnomalyArray = DummyDataFetcher.getDummyData();
		String url = Struts2Utils.getRequest().getContextPath() + "/report/flashchart/report2-drilldown.action?id=";

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("chart");
		Element series = root.addElement("series");
		Element graphs = root.addElement("graphs");
		Element graph1 = graphs.addElement("graph").addAttribute("gid", "1");
		Element graph2 = graphs.addElement("graph").addAttribute("gid", "2");

		int index = 1;
		for (TemperatureAnomaly data : temperatureAnomalyArray) {
			series.addElement("value").addAttribute("xid", String.valueOf(index)).addText(
					String.valueOf(data.getYear()));

			Element graph1Value = graph1.addElement("value").addAttribute("xid", String.valueOf(index)).addText(
					String.valueOf(data.getAnomaly()));

			graph1Value.addAttribute("url", url + index);

			if (data.getAnomaly() < 0) {
				graph1Value.addAttribute("color", "#318DBD");
			}

			graph2.addElement("value").addAttribute("xid", String.valueOf(index)).addText(
					String.valueOf(data.getSmoothed()));

			index++;
		}

		HttpServletResponse response = Struts2Utils.getResponse();
		response.setContentType("text/xml;charset=utf-8");

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xmlWriter = new XMLWriter(response.getWriter(), format);
		xmlWriter.write(document);
		xmlWriter.close();

		return null;
	}
}
