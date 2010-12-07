<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Showcase 登录页</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>Showcase登录页</h1>

		<%if ("1".equals(request.getParameter("error"))) {%>
			<div class="error"> 用户名密码错误,请重试.</div>
		<%
		}
		if ("2".equals(request.getParameter("error"))) {
		%>
			<div class="error"> 验证码错误,请重试.</div>
		<%
		}
		if ("3".equals(request.getParameter("error"))) {
		%>
			<div class="error"> 此帐号已从别处登录.</div>
		<%}%>
	
		<form id="loginForm" action="${ctx}/j_spring_security_check" method="post">
			<table class="noborder">
				<tr>
					<td>用户名:</td>
					<td>
						<input type='text' name='j_username' size='10'
						<s:if test="not empty param.error">
							value='<%=session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</s:if> />
					</td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type='password' size='10' name='j_password'/></td>
				</tr>
				<tr>
					<td><input type="checkbox" name="_spring_security_remember_me"/>
						两周内记住我</span>
					</td>
				</tr>
				<tr>
					<td><input value="登录" type="submit"/></td>
				</tr>
			</table>
		</form>
		<span>（管理员<b>admin/admin</b> ,普通用户<b>user/user</b>）<a href="${ctx}/j_spring_security_logout">退出登录</a></span>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>

