<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>综合演示用例</title>
</head>

<body>
		<h2>综合演示用例</h2>
		<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else>用户</h3>

		<form id="inputForm" action="${ctx}/common/user!save.action" method="post">
			<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="workingVersion" value="${version}"/>
			<table class="noborder">
				<tr>
					<td>登录名:</td>
					<td><input type="text" name="loginName" size="40" value="${loginName}"/></td>
				</tr>
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="name" size="40" value="${name}"/></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" name="plainPassword" size="40" value="${plainPassword}"/></td>
				</tr>
				<tr>
					<td>密码散列:</td>
					<td>${shaPassword}</td>
				</tr>

				<tr>
					<td>创建:</td>
					<td>${createBy} <fmt:formatDate value="${createTime}" type="both"/></td>
				</tr>
				<tr>
					<td>最后修改:</td>
					<td>${lastModifyBy} <fmt:formatDate value="${lastModifyTime}" type="both"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交"/>&nbsp;
						<input type="button" value="返回" onclick="history.back()"/>
					</td>
				</tr>
			</table>
		</form>
</body>
</html>