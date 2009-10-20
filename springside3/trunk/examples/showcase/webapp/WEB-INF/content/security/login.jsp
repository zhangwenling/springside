<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Showcase 登录页</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		function refreshCaptcha() {
			$('#captchaImg').html('<img src="${ctx}/security/jcaptcha.jpg?' + Math.round(Math.random() * 100000) + '"/>');
		}
	</script>

</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<%if ("1".equals(request.getParameter("error"))) {%>
		<span style="color:red"> 用户名密码错误,请重试.</span>
		<%
			}
			if ("2".equals(request.getParameter("error"))) {
		%>
		<span style="color:red"> 验证码错误,请重试.</span>
		<%
			}
			if ("3".equals(request.getParameter("error"))) {
		%>
		<span style="color:red"> 此帐号已从别处登录.</span>
		<%}%>
		<h1>Showcase登录页</h1>

		<h2>技术说明：</h2>

		<p>基于JCaptcha1.0的验证码方案，通过ServletFilter与SpringSecurity简单集成。</p>

		<form id="loginForm" action="${ctx}/j_spring_security_check" method="post">
			<table class="noborder">
				<tr>
					<td>用户名:</td>
					<td>
						<input type='text' name='j_username' size='10'
						<s:if test="not empty param.error"> value='<%=session
								.getAttribute(AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>
							'</s:if>/>
					</td>
					<td rowspan="3"><span id="captchaImg"><img src="${ctx}/security/jcaptcha.jpg"/></span></td>

				</tr>
				<tr>
					<td>密码:</td>
					<td><input type='password' size='10' name='j_password'/></td>
				</tr>
				<tr>
					<td>验证码:</td>
					<td><input type='text' name='j_captcha' size='5'/></td>
				</tr>
				<tr>
					<td colspan='3'><input type="checkbox" name="_spring_security_remember_me"/>
						两周内记住我 <span style="margin-left:25px"><a href="javascript:refreshCaptcha()">看不清楚换一张</a></span>
					</td>
				</tr>
				<tr>
					<td colspan='3'><input value="登录" type="submit"/></td>
				</tr>
			</table>
		</form>
		<span>（管理员<b>admin/admin</b> ,普通用户<b>user/user</b>）<a href="${ctx}/j_spring_security_logout">退出登录</a></span>
	</div>
</div>
</body>
</html>

