package org.springside.modules.orm.jdbc;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SqlBuilder {

	private Template template;

	public SqlBuilder(String sqlTemplate) {
		try {
			template = new Template("sql", new StringReader(sqlTemplate), new Configuration());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getSql(Map model) {
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
}
