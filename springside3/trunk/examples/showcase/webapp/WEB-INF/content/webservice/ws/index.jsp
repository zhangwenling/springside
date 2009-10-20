<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>WebService高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>WS-*高级协议演示</h1>

		<h2>技术说明:</h2>
		<ul>
			<li>WS-Security认证机制：符合WS互操作规范的标准认证机制,演示Plain与Digest式的密码认证.</li>
			<li>MTOM:附件机制.(计划中)</li>
		</ul>
		<h2>用户故事:</h2>
		<ul>
			<li>Plain式密码,传输时安全度较低,但不要求服务端必须保存有客户密码的明文.<br/>
				WSDL: <a href="http://localhost:8080/showcase/services/UserServiceWithPlainPassword?wsdl">http://localhost:8080/showcase/services/UserServiceWithPlainPassword?wsdl</a>
			</li>
			<li>Digest式密码,传输时较为安全,但要求服务端必须保存有kehu密码的明文<br/>
				WSDL: <a href="http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl">http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl</a>
			</li>
			<li>客户端的用户名与密码设定见测试用例.</li>
		</ul>
	</div>
</div>
</body>
</html>