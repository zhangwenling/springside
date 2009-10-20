<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>定时任务演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>定时任务演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>JDK5.0 ScheduledExecutorService Timer式任务定义.</li>
			<li>Quartz Timer式与Cron式任务定义.</li>
			<li>Quartz 任务在内存或数据库中存储, 单机或集群执行演示.</li>
		</ul>

		<h2>用户故事：</h2>
		<ul>
			<li>简单的定时在Console打印当前用户数量.</li>
			<li>同时运行jetty-run-node1.bat与jetty-run-node2.bat 演示集群运行的效果.</li>
		</ul>

	</div>
</div>
</body>
</html>