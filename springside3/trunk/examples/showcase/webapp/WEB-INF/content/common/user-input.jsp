<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>综合演示用例</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet">
</head>

<body>
<h3><s:if test="id == null">创建</s:if><s:else>修改</s:else>用户</h3>
<div id="inputContent">
<form id="inputForm" action="user!save.action" method="post">
<input type="hidden" name="id" value="${id}" />
<table>
<tr>
		<td>登录名:</td>
		<td><input type="text" name="loginName" size="40" value="${loginName}"/></td>
	</tr>
	<tr>
		<td>用户名:</td>
		<td><input type="text" name="name" size="40" value="${name}"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="提交" />&nbsp; 
			<input type="button" value="取消" onclick="history.back()"/>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>