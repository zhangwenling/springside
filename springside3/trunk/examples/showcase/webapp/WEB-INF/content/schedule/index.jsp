<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>定时任务演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>定时任务演示</h3>
<h4>技术说明：</h4>
<ul>
<li>Cron式任务定义：基于Quartz的实现。</li>
<li>Timer式任务定义：基于Quartz与JDK5.0 ScheduledExecutorService的两种实现</li>
</ul>


<h4>用户故事：</h4>简单的定时打印日志。
<h4>未来版本：</h4>Quartz的高级配置及在集群中的使用。
</div>
</div>
</body>
</html>