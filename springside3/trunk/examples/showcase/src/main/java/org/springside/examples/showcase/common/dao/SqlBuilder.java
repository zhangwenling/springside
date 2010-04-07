package org.springside.examples.showcase.common.dao;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

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

	public String getSql(Map conditionMap) {
		try {
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, conditionMap);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
}
