#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 角色管理</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${symbol_dollar}{ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<div id="message"><s:actionmessage theme="mytheme"/></div>

	<div id="filter">你好, <%=SpringSecurityUtils.getCurrentUserName()%>.</div>

	<div>
		<table id="contentTable">
			<tr>
				<th>名称</th>
				<th>授权</th>
				<th>操作</th>
			</tr>

			<s:iterator value="allRoleList">
				<tr>
					<td>${symbol_dollar}{name}</td>
					<td>${symbol_dollar}{authNames}</td>
					<td>&nbsp;
						<security:authorize ifAnyGranted="A_VIEW_ROLE">
							<security:authorize ifNotGranted="A_MODIFY_ROLE">
								<a href="role!input.action?id=${symbol_dollar}{id}">查看</a>&nbsp;
							</security:authorize>
						</security:authorize>

						<security:authorize ifAnyGranted="A_MODIFY_ROLE">
							<a href="role!input.action?id=${symbol_dollar}{id}">修改</a>&nbsp;
							<a href="role!delete.action?id=${symbol_dollar}{id}">删除</a>
						</security:authorize>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>

	<div>
		<security:authorize ifAnyGranted="A_MODIFY_ROLE">
			<a href="role!input.action">增加新角色</a>
		</security:authorize>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</body>
</html>