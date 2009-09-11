<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Flash Chart演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/report/swfobject.js"></script>
<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>Flash Chart演示</h3>
<p>点击Amcharts柱状图进入本页面, 显示<%=Integer.parseInt(request.getParameter("id"))+1969%>年的数据细节.</p>
</div>
</div>
</body>
</html>