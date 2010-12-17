<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>日志高级演示</title>
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
		<h2>日志高级演示</h2>

		<h3>技术说明：</h3>
		<ul>
			<li>Log4JMBean: 通过JMX动态查询与改变Logger的日志等级与Appender.</li>
			<li>MockLog4jAppender: 在测试用例中验证日志的输出.</li>
			<li>TraceLogAspect/TraceUtils/@Traced: 方便跟踪问题的调试信息使用,详见wiki.</li>
		</ul>
		
		<h3>用户故事：</h3>
		<ul>
			<li>使用JConsole动态修改log4j的日志等级.(路径service:jmx:rmi:///jndi/rmi://localhost:2099/jmxrmi,名称Log4j:name=log4j)</li>
			<li>Schedule测试用例使用MockAppender校验日志输出.</li>
			<li>UserWebService服务通过TraceLogAspect, 使用TraceUtils在Log4j MDC中设置TraceId.</li>
			<li>每次进入本页面, logger都会通过AOP自动生成一条调试记录，记录在另外的showcase_trace.log.</li>
			</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>