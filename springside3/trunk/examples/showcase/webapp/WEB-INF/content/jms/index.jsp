<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMS演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h2>JMS演示</h2>
<p>技术说明：演示基于ActiveMQ的JMS Topic/Queue 以及Polling消费者 和Message Driven POJO。</p>
<p>用户故事：<br/>
   在综合演示示例中创建用户时，将消息发送到Queue中，Polling消费者接收消息，发送用户通知邮件。<br/>
   在综合演示示例中删除用户时，将消息发送到Topic中，两类不同的Message Driven POJO接收消息，打印不同的日志。
</p>

<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>