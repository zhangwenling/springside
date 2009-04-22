<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Ajax演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h2>Ajax演示</h2>
<p>说明：在JMX示例中已演示基于JQuery的标准Ajax获取内容与表单提交。</p>
<ul>
<li><a href="${ctx}/ajax/mashup/mashup-client.action">跨域Mashup演示</a></li>
<li><a href="${ctx}/ajax/dwr/index.action">DWR演示(计划中)</a></li>
</ul>
</div>
</div>
</body>
</html>