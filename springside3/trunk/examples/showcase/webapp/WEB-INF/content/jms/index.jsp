<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMS演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>JMS演示</h3>
<h4>技术说明：</h4>
演示基于ActiveMQ的JMS Topic/Queue 以及Polling消费者 和Message Driven POJO。
<h4>用户故事：</h4>
<ul>
   <li>在综合演示示例中创建用户时，将消息发送到Queue中，Polling消费者接收消息，发送用户通知邮件。</li>
   <li>在综合演示示例中删除用户时，将消息发送到Topic中，两类不同的Message Driven POJO接收消息，打印不同的日志。</li>
</ul>
</div>
</div>
</body>
</html>