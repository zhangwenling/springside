package org.springside.modules.orm.hibernate;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;

public class IgnorePrefixReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy {

	private Set<String> printed = new HashSet<String>();

	public IgnorePrefixReverseEngineeringStrategy(ReverseEngineeringStrategy delegate) {
		super(delegate);
	}

	@Override
	public String tableToClassName(TableIdentifier tableIdentifier) {
		String delegateResult = super.tableToClassName(tableIdentifier);
		int index = delegateResult.lastIndexOf('.');
		String className = delegateResult.substring(0, index) + "."
				+ delegateResult.substring(index + getPrefixLength());

		if (!printed.contains(className)) {
			System.out.println("<mapping class=\"" + className + "\" >");
			printed.add(className);
		}

		return className;
	}

	protected int getPrefixLength() {
		return 5;
	}
}
