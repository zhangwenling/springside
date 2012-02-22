<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<html>
<head>
	<title>综合演示用例</title>
	<script language="javascript">
		function disableUsers() {
			$("#mainForm").submit();
		}
	</script>
</head>

<body>
		<h2>综合演示用例</h2>
		<%if(SecurityUtils.getSubject().getPrincipal()!=null){ %>
		<div>你好, 用户<%=SecurityUtils.getSubject().getPrincipal()%>登录.&nbsp;&nbsp;</div>
		<%} %>
		<div>
			<form id="mainForm" action="user!disableUsers.action" method="post">
				<table id="contentTable">
					<tr>
						<th>&nbsp;</th>
						<th>登录名</th>
						<th>姓名</th>
						<th>电邮</th>
						<th>角色</th>
						<th>状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${users}" var="user">
						<tr>
							<td><input type="checkbox" name="checkedUserIds" value="${id}"/></td>
							<td>${user.loginName}&nbsp;</td>
							<td>${user.name}&nbsp;</td>
							<td>${user.email}&nbsp;</td>
							<td>${user.roleNames}&nbsp;</td>
							<td>${user.status}&nbsp;</td>
							<td><a href="user!input.action?id=${id}" id="editLink-${id}">修改</a></td>
						</tr>
					</c:forEach>
					
				</table>
				<input type="submit" value="暂停选中用户"/>
			</form>
		</div>
</body>
</html>
