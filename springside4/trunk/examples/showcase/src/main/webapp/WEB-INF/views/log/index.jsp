<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>日志高级演示</title>
</head>

<body>
	<h2>日志高级演示</h2>

	<h3>技术说明：</h3>
	<ul>
		<li>Log4JMBean: 通过JMX动态查询与改变Logger的日志等级与Appender.</li>
		<li>MockLog4jAppender: 在测试用例中验证日志的输出.</li>
	</ul>

	<h3>用户故事：</h3>
	<ul>
		<li>使用JConsole动态修改log4j的日志等级.(路径service:jmx:rmi:///jndi/rmi://localhost:2099/jmxrmi,名称Log4j:name=log4j)</li>
		<li>Schedule测试用例使用MockAppender校验日志输出.</li>
	</ul>
</body>
</html>