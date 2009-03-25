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
<p>技术说明：演示基于Quartz的定时任务，包括Timer与Cron两种Trigger及Quartz在集群环境的使用。</p>
<p>用户故事：简单的定时打印日志。</p>

<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>