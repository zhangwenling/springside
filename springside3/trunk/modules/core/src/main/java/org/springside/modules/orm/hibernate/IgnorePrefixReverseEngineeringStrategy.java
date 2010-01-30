package org.springside.modules.orm.hibernate;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

/**
 * Hibernate Tools 从数据库逆向生成Entity POJO时, Class名要去除表名中的前缀的策略.
 * @author ehuaxio
 *
 */
public class IgnorePrefixReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

	private Set<String> printed = new HashSet<String>();

	public IgnorePrefixReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	public String tableToClassName(TableIdentifier tableIdentifier) {
		String delegateResult = super.tableToClassName(tableIdentifier);
		int index = delegateResult.lastIndexOf('.');
		String className = delegateResult.substring(0, index + 1)
				+ delegateResult.substring(index + getPrefixLength() + 1);

		if (!printed.contains(className)) {
			System.out.println("<mapping class=\"" + className + "\" >");
			printed.add(className);
		}

		return className;
	}

	protected int getPrefixLength() {
		return 4;
	}
}
