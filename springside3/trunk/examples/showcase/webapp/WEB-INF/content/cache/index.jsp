<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Cache演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>Cache演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>演示在Spring中使用Ehcache.</li>
		</ul>
		
		<h2>用户故事：</h2>
		<ul>
			<li>在Web高级演示的ContentServlet中使用Ehcache缓存静态文件的元数据.</li>
		</ul>
	</div>
</div>
</body>
</html>