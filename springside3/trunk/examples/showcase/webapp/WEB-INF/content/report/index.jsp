<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>报表演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<script type="text/javascript" src="${ctx}/report/swfobject.js"></script>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>报表演示</h1>

		<p>说明：演示DIY报表所需技术。</p>
		<ul>
			<li><a href="flashchart/index.action">amCharts渲染Flash图表</a></li>
			<li><a href="excel/index.action">POI导出Excel文件</a></li>
		</ul>
	</div>
</div>
</body>
</html>