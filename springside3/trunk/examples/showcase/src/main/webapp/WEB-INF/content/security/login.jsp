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
	
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
		
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</head>

<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last">
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
					<td><label for="j_username">用户名:</label></td>
					<td>
						<input type='text' name='j_username' size='10' class="required"
						<s:if test="not empty param.error">
							value='<%=session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</s:if> />
					</td>
				</tr>
				<tr>
					<td><label for="j_password">密码:</label></td>
					<td><input type='password' size='10' name='j_password' class="required"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me"/>
						<label for="_spring_security_remember_me">两周内记住我</label>
						<input value="登录" type="submit"/>
					</td>
				</tr>
			</table>
		</form>
		<div>（管理员<b>admin/admin</b> ,普通用户<b>user/user</b>）<a href="${ctx}/j_spring_security_logout">退出登录</a></div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>

