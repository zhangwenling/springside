<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>综合演示用例</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet">
	<link href="${ctx}/css/table.css" type="text/css" rel="stylesheet">
	
	<script language="javascript">
	function disableUsers(){
		$("#mainForm").submit();
	}
	</script>
</head>

<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>综合演示用例</h3>

<div id="listContent">
<form id="mainForm" action="user!disableUsers.action" method="post">
<table id="listTable">
	<tr>
		<th>&nbsp;</th>
		<th><b>登录名</b></th>
		<th><b>姓名</b></th>
		<th><b>电邮</b></th>
		<th><b>角色</b></th>
		<th><b>状态</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="userList">
		<tr>
			<td><input type="checkbox" name="checkedUserIds" value="${id}"/></td>
			<td>${loginName}&nbsp;</td>
			<td>${name}&nbsp;</td>
			<td>${email}&nbsp;</td>
			<td>${roleNames}&nbsp;</td>
			<td>${status}&nbsp;</td>
			<td><a href="user!input.action?id=${id}">修改</a></td>
		</tr>
	</s:iterator>
</table>
<input type="submit" value="暂停用户"/>
</form>
</div>
</div>
</div>
</body>
</html>
