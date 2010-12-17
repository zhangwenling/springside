<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>定时任务演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>	
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
</head>

<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last">
		<h2>定时任务演示</h2>

		<h3>技术说明：</h3>
		<ul>
			<li>JDK5.0 ScheduledExecutorService的Timer式任务定义, 支持Graceful Shutdown演示.</li>
			<li>Spring的Cront式任务定义</li>
			<li>Quartz的Timer式与Cron式任务定义.</li>
			<li>Quartz的任务在内存或数据库中存储, 单机或集群执行演示.</li>
		</ul>

		<h3>用户故事：</h3>
		<ul>
			<li>简单的定时在Console打印当前用户数量.</li>
			<li>设法同时运行两个实例, 演示集群运行的效果.</li>
		</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>