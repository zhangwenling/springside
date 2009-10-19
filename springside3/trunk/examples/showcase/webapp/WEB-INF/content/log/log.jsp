<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日志高级演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="main">
<h1>日志高级演示</h1>
<h2>技术说明：</h2>
<ul>
<li>Log4JMBean: JMX动态改变log4j的日志等级,并查询Logger的Appender.</li>
<li>MockAppender: 在测试用例中验正日志输出.</li>
<li>TraceUtils: 输出可方便Trace的业务系统运行调试信息.</li>
<li>AsyncQueueAppender:轻量级日志异步多线程处理框架</li>

</ul>
<h4>用户故事：</h4>
<ul>
<li>使用JConsole动态修改log4j的日志等级.(路径service:jmx:rmi:///jndi/rmi://localhost:1099/showcase,名称SpringSide:type=Log4jManagement)</li>
<li>Schedule测试用例使用MockAppender校验日志输出.</li>
<li>UserWebService使用TraceUtils打印Trace信息.</li>
<li>每次进入本页面, logger都会在数据库LOGS表中增加一条记录.</li>
</ul>	
</div>
</div>
</body>
</html>