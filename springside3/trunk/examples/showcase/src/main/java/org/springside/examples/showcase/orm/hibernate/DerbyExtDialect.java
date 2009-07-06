package org.springside.examples.showcase.orm.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.dialect.DerbyDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * 演示扩展DerbyDialect,增加两种函数.
 * 
 * @author calvin
 */
public class DerbyExtDialect extends DerbyDialect {

	public DerbyExtDialect() {
		super();
		registerFunction("up", new StandardSQLFunction("upper"));
		registerFunction("sample", new SQLFunctionTemplate(Hibernate.DOUBLE, "random()*100", true));
	}
}
