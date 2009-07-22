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
<h3>Ajax演示</h3>
<p>说明：在<a href="${ctx}/jmx/jmx-client.action">JMX示例</a>中已演示基于JQuery的标准Ajax获取内容并提交表单。</p>
<ul>
	<li><a href="${ctx}/ajax/mashup/mashup-client.action">跨域Mashup演示</a></li>
</ul>
</div>
</div>
</body>
</html>