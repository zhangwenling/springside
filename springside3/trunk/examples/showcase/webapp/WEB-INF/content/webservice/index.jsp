<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>WebService高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="main">
<h1>Web Service高级演示</h1>
<ul>
<li><a href="${ctx}/webservice/ws/index.action">WS-*高级协议演示(安全、附件)</a></li>
<li><a href="${ctx}/webservice/rest/index.action">RESTful Web Service演示</a></li>
<li><a href="${ctx}/webservice/hessian/index.action">Hessian 演示</a></li>
</ul>
</div>
</div>
</body>
</html>