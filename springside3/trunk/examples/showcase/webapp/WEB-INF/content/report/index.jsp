<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>报表演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/report/swfobject.js"></script>
<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h2>报表演示</h2>
<p>说明：演示DIY报表所需技术。</p>
<ul>
<li><a href="flashchart/index.action">amCharts渲染Flash图表</a></li>
<li>POI导出Excel文件(计划中)</li>
<li>iText导出Pdf文件(计划中)</li>
</ul>
</div>
</div>
</body>
</html>