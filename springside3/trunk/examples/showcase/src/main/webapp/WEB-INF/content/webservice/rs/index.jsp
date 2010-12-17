<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Restful Service高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last">
		<h1>Restful Service 高级演示</h1>

		<h2>技术说明:</h2>
		<ul>
			<li>HttpBasic认证, 与SpringSecurity安全控制结合,</li>
			<li>根据不同的输入参数, 返回不同编码格式(html/json),及格式特定的内容(html字符串/java对象)</li>
			<li>获取灵活的，不固定的输入参数.</li>
		</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>