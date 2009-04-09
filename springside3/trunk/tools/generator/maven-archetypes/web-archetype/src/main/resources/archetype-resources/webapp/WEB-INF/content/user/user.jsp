#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 帐号管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${symbol_dollar}{ctx}/css/default.css" type="text/css" rel="stylesheet">
</head>

<body>
<div id="menu">
	<h3>
	<a href="${symbol_dollar}{ctx}/user/user.action">帐号列表</a>
	<a href="${symbol_dollar}{ctx}/user/role.action">角色列表</a> 
	<a href="${symbol_dollar}{ctx}/j_spring_security_logout">退出登录</a>
	</h3> 
</div>

<div id="message"><s:actionmessage theme="mytheme"/></div>

<div id="filter">
<form action="user!search.action" method="post">
	你好,<%=SpringSecurityUtils.getCurrentUserName()%>.&nbsp;&nbsp;
 	登录名: <input type="text" name="filter_EQUAL_loginName" value="${symbol_dollar}{param['filter_EQUAL_loginName']}"  size="8"/> 
          姓名或Email: <input type="text" name="filter_LIKE_name|email" value="${symbol_dollar}{param['filter_LIKE_name|email']}" size="8"/>
	<input type="submit" value="搜索" />
</form>
</div> 

<div id="listContent">
<table>
	<tr>
		<th><a href="user.action?page.orderBy=loginName&page.order=
		<s:if test="page.orderBy=='loginName'">${symbol_dollar}{page.inverseOrder}</s:if><s:else>desc</s:else>">
		<b>登录名</b></a></th>
		<th><a href="user.action?page.orderBy=name&page.order=
		<s:if test="page.orderBy=='name'">${symbol_dollar}{page.inverseOrder}</s:if><s:else>desc</s:else>">
		<b>姓名</b></a></th>
		<th><a href="user.action?page.orderBy=email&page.order=
		<s:if test="page.orderBy=='email'">${symbol_dollar}{page.inverseOrder}</s:if><s:else>desc</s:else>">
		<b>电邮</b></a></th>
		<th><b>角色</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="page.result">
		<tr>
			<td>${symbol_dollar}{loginName}&nbsp;</td>
			<td>${symbol_dollar}{name}&nbsp;</td>
			<td>${symbol_dollar}{email}&nbsp;</td>
			<td>${symbol_dollar}{roleNames}&nbsp;</td>
			<td>&nbsp; 
				<security:authorize ifAnyGranted="A_MODIFY_USER">
					<a href="user!input.action?id=${symbol_dollar}{id}&page.pageRequest=${symbol_dollar}{page.pageRequest}">修改</a>、
					<a href="user!delete.action?id=${symbol_dollar}{id}&page.pageRequest=${symbol_dollar}{page.pageRequest}">删除</a>
				</security:authorize>
			</td>
		</tr>
	</s:iterator>
</table>
</div>

<div id="footer">
	第${symbol_dollar}{page.pageNo}页, 共${symbol_dollar}{page.totalPages}页 
	<s:if test="page.hasPre">
		<a href="user.action?page.pageNo=${symbol_dollar}{page.prePage}&page.orderBy=${symbol_dollar}{page.orderBy}&page.order=${symbol_dollar}{page.order}">上一页</a>
	</s:if>
	<s:if test="page.hasNext">
		<a href="user.action?page.pageNo=${symbol_dollar}{page.nextPage}&page.orderBy=${symbol_dollar}{page.orderBy}&page.order=${symbol_dollar}{page.order}">下一页</a>
	</s:if>
	<security:authorize ifAnyGranted="A_MODIFY_USER">
		<a href="user!input.action">增加新用户</a>
	</security:authorize>
</div>

<div id="comment">本页面为单纯的白板,各式Table Grid组件的应用见Showcase项目(开发中).</div>
</body>
</html>
