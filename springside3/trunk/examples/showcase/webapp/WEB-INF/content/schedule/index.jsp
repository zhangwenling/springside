<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>定时任务演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h2>定时任务演示</h2>
<p>技术说明：<br/>
Cron式任务定义：基于Quartz的实现。<br/>
Timer式任务定义:基于Quartz与JDK5.0 ScheduledExecutorService的两种实现。如果执行长时间任务，执行时间长于间隔时间，Quartz会并发执行下一任务而ScheduledExecutorService不会。


</p>
<p>用户故事：简单的定时打印日志。</p>
<p>未来版本：Quartz的高级配置及在集群中的使用。</p>

<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>