<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>Mini-Web 帐号管理</title>
	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						required: true,
						remote: "user!checkLoginName.action?oldLoginName=" + encodeURIComponent('${loginName}')
					},
					name: "required",
					password: {
						required: true,
						minlength:3
					},
					passwordConfirm: {
						equalTo:"#password"
					},
					email:"email",
					checkedGroupIds:"required"
				},
				messages: {
					loginName: {
						remote: "用户登录名已存在"
					},
					passwordConfirm: {
						equalTo: "输入与上面相同的密码"
					}
				}
			});
		});
	</script>
</head>

<body>
	<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else>用户</h3>
	<form:form id="inputForm" modelAttribute="user" action="save" method="post">
		<input type="hidden" name="id" value="${user.id}"/>
		<table class="noborder">
			<tr>
				<td>登录名:</td>
				<td><input type="text" name="loginName" size="40" id="loginName" value="${user.loginName}"/></td>
			</tr>
			<tr>
				<td>用户名:</td>
				<td><input type="text" id="name" name="name" size="40" value="${user.name}"/></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" id="password" name="password" size="40" value="${user.password}"/></td>
			</tr>
			<tr>
				<td>确认密码:</td>
				<td><input type="password" id="passwordConfirm" name="passwordConfirm" size="40" value="${user.password}"/>
				</td>
			</tr>
			<tr>
				<td>邮箱:</td>
				<td><input type="text" id="email" name="email" size="40" value="${user.email}"/></td>
			</tr>
			<tr>
				<td>权限组:</td>
				<td>
				<form:checkboxes path="groupIds" items="${groups}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					
						<input class="button" type="submit" value="提交"/>&nbsp;	
					<input class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
