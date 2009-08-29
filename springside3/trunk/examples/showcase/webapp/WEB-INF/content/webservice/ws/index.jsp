<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>WebService高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>WS-*高级协议演示</h3>
<h4>技术说明:</h4>
<ul>
<li>WS-Security认证机制：符合WS互操作规范的标准认证机制,演示Plain与Digest式的密码认证.</li>
<li>MTOM:附件机制.</li>
</ul>
<h4>用户故事:</h4>
<ul>
<li>Plain密码WSDL: <a href="http://localhost:8080/showcase/services/UserServiceWithPlainPassword?wsdl">http://localhost:8080/showcase/services/UserServiceWithPlainPassword?wsdl</a></li>
<li>Digest式密码WSDL: <a href="http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl">http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl</a></li>
</ul>
</div>
</div>
</body>
</html>