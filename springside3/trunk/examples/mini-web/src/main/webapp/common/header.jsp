<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.springside.modules.security.springsecurity.SpringSecurityUtils" %>
<%@ include file="/common/taglibs.jsp" %>
<div id="header" class="span-24 last">
	<div id="title">
		<h2>Mini-Web示例</h2>
		<h3>--CRUD管理界面演示</h3>
	</div>
	<div id="menu">
		<ul>
			<li><a href="${ctx}/account/user.action">帐号列表</a></li>
			<li><a href="${ctx}/account/role.action">角色列表</a></li>
			<li><a href="${ctx}/login!logout.action">退出登录</a></li>
		</ul>
	</div>
</div>