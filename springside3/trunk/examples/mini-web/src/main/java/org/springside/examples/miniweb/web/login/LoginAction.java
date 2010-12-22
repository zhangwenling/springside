/*
 * $HeadURL: $
 * $Id: $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package org.springside.examples.miniweb.web.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springside.examples.miniweb.web.CrudActionSupport;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author eortwyz
 */
//定义URL映射对应/login.action
@Namespace("/")
//定义名为reload的result重定向到login.action, 其他result则按照convention默认.
@Results( { @Result(name = CrudActionSupport.SUCCESS, location = "account/user.action", type = "redirect") })
public class LoginAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private String            username;

    private String            password;

    private String            rememberme;

    @Override
    public String execute() throws Exception {
        return "login";
    }

    public String login() {
        boolean isSuccess = false;
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            SecurityUtils.getSubject().login(token);
            isSuccess = true;
        } catch (AuthenticationException e) {
            isSuccess = false;
        }

        if (isSuccess) {
            return SUCCESS;
        } else {
            return "login";
        }
    }

    public String logout() {
        SecurityUtils.getSubject().logout();

        return "login";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberme() {
        return rememberme;
    }

    public void setRememberme(String rememberme) {
        this.rememberme = rememberme;
    }

}
