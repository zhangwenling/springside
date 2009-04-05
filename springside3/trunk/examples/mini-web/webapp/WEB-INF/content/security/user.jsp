<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 帐号管理</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script  src="${ctx}/js/table.js" type="text/javascript"></script>
	<script type="text/javascript">
		var modifyAction = 'user!input.action';
		var deleteAction = 'user!delete.action';
	</script>
</head>

<body>
<div id="menu">
	<h3>
	<a href="${ctx}/security/user.action">帐号列表</a>
	<a href="${ctx}/security/role.action">角色列表</a> 
	<a href="${ctx}/j_spring_security_logout">退出登录</a>
	</h3> 
</div>

<div id="message"><s:actionmessage theme="mytheme"/></div>

<div id="filter">
<form id="searchForm" action="user!search.action" method="get">
	你好,<%=SpringSecurityUtils.getCurrentUserName()%>.&nbsp;&nbsp;
 	登录名: <input type="text" name="filter_EQUAL_loginName" value="${param['filter_EQUAL_loginName']}"  size="9"/> 
          姓名或Email: <input type="text" name="filter_LIKE_name|email" value="${param['filter_LIKE_name|email']}" size="9"/>
	<input type="submit" value="搜索" />
</form>
</div> 
<form id="mainForm" action="user.action" method="get">
<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
<input type="hidden" name="page.order" id="order" value="${page.order}" />
<input type="hidden" name="id" id="id" />
<div id="listContent">
<table>
	<tr>
		<th><a href="#" onclick="order('loginName','asc')"><b>登录名</b></a></th>
		<th><a href="#" onclick="order('name','asc')""><b>姓名</b></a></th>
		<th><a href="#" onclick="order('email','asc')"><b>电邮</b></a></th>
		<th><b>角色</b></th>
		<th><b>操作</b></th>
	</tr>

	<s:iterator value="page.result">
		<tr>
			<td>${loginName}&nbsp;</td>
			<td>${name}&nbsp;</td>
			<td>${email}&nbsp;</td>
			<td>${roleNames}&nbsp;</td>
			<td>&nbsp; 
				<security:authorize ifAnyGranted="A_MODIFY_USER">
					<a href="#" onclick="modifyItem(${id})">修改</a>、
					<a href="#" onclick="deleteItem(${id})">删除</a>
				</security:authorize>
			</td>
		</tr>
	</s:iterator>
</table>
</div>

<div id="footer">
	第${page.pageNo}页, 共${page.totalPages}页 
	<a href="#" onclick="jumpPage(1)">首页</a>
	<s:if test="page.hasPre"><a href="#" onclick="jumpPage(${page.prePage})">上一页</a></s:if>
	<s:if test="page.hasNext"><a href="#" onclick="jumpPage(${page.nextPage})">下一页</a></s:if>
	<a href="#" onclick="jumpPage(${page.totalPages})">末页</a>
	
	<security:authorize ifAnyGranted="A_MODIFY_USER">
		<a href="user!input.action">增加新用户</a>
	</security:authorize>
</div>
</form>
</body>
</html>
