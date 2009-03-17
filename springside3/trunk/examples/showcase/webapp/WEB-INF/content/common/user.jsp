<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CRUD 简略写法示例</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet">
</head>

<body>
<div id="listContent">
<table>
	<tr>
		<th><b>登录名</b></th>
		<th><b>姓名</b></th>
		<th><b>电邮</b></th>
		<th><b>角色</b></th>
	</tr>

	<s:iterator value="allUsers">
		<tr>
			<td>${loginName}&nbsp;</td>
			<td>${name}&nbsp;</td>
			<td>${email}&nbsp;</td>
			<td>${roleNames}&nbsp;</td>
		</tr>
	</s:iterator>
</table>
</div>
<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>
