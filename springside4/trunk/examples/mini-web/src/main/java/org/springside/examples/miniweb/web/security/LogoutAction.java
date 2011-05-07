/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2011 by Ericsson, all rights reserved.
 */

package org.springside.examples.miniweb.web.security;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Namespace;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author eortwyz
 */
@Namespace("/")
public class LogoutAction extends ActionSupport {

	private static final long serialVersionUID = 7392913081177740732L;

	@Override
	public String execute() throws Exception {
		SecurityUtils.getSubject().logout();
		return "login";
	}
}
