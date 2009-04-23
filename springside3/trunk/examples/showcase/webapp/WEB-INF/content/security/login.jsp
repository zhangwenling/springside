<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Showcase 登录页</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
	<%
			if("1".equals(request.getParameter("error"))){
	%>
			<span style="color:red"> 用户名密码错误,请重试.</span>
	<%
			}
			if("2".equals(request.getParameter("error"))){	
	%>
			<span style="color:red"> 验证码错误,请重试.</span>
	<%
			}
			if("3".equals(request.getParameter("error"))){
	%>
			<span style="color:red"> 此帐号已从别处登录.</span>
	<%
			}
	%>
	<h2>Showcase登录页</h2>
	<h4>技术说明：</h4>基于JCaptcha1.0的验证码方案，通过Filter与SpringSecurity简单集成。
	
	<form id="loginForm" action="${ctx}/j_spring_security_check" method="post">
		<table class="inputView">
			<tr>
				<td>用户名:</td>
				<td colspan='2'>
					<input type='text' name='j_username'
					<s:if test="not empty param.error"> value='<%=session.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</s:if>/>
				</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td  colspan='2'><input type='password' name='j_password' /></td>
			</tr>
			<tr>
				<td>验证码:</td>
				<td><input type='text' name='j_captcha'/><br/>
				(可更换较美观的JCaptcha2.0)</td>
				<td><img src="${ctx}/security/jcaptcha.jpg" /></td>
			</tr>
			<tr>
				<td colspan='3'></td>
			</tr>
			
			<tr>
				<td>
					<input type="checkbox" name="_spring_security_remember_me" />
				</td>
				<td colspan='2'>两周内记住我</td>
			</tr>
			<tr>
				<td colspan='3'><input value="登录" type="submit" /></td>
			</tr>
		</table>
	</form>
	<p>（管理员<b>admin/admin</b> ,普通用户<b>user/user</b>）<a href="${ctx}/j_spring_security_logout">退出登录</a></p>
	</div>
	</div>
</body>
</html>

