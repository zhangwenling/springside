<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>综合演示用例</title>
	<%@ include file="/common/meta.jsp" %>

	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->

	<script language="javascript">
		function disableUsers() {
			$("#mainForm").submit();
		}
	</script>
</head>

<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last prepend-top">
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

					<s:iterator value="allUserList">
						<tr>
							<td><input type="checkbox" name="checkedUserIds" value="${id}"/></td>
							<td>${loginName}&nbsp;</td>
							<td>${name}&nbsp;</td>
							<td>${email}&nbsp;</td>
							<td>${roleNames}&nbsp;</td>
							<td>${status}&nbsp;</td>
							<td><a href="user!input.action?id=${id}" id="editLink-${id}">修改</a></td>
						</tr>
					</s:iterator>
				</table>
				<input type="submit" value="暂停选中用户"/>
			</form>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>
