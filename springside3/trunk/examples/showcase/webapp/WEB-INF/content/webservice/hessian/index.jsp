<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Hessian Web Service演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>Hessian Web Service演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>Hessian基于Http端口,使用二进制数据协议,支持跨语言实现,主要用于内部系统间的高性能服务调用.</li>
		</ul>
		<h2>用户故事:</h2>
		<ul>
			<li>客户端调用见功能测试用例.</li>
		</ul>
	</div>
</div>
</body>
</html>