<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMS演示</title>
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
		<h2>JMS演示</h2>

		<h32>技术说明：</h3>
		<ul>
			<li>演示基于ActiveMQ的JMS Topic/Queue应用</li>
			<li>演示基于Spring CachingConnectionFactory, JmsTemplate, DefaultMessageListener的应用</li>
			<li>演示使用默认值的Simple模式</li>
			<li>演示Advanced模式, 包括发送者的timeToLive等属性设置, 接受者的消息过滤器,消息确认模式与持久化订阅者 </li>
		</ul>

		<h3>用户故事：</h3>
		<ul>
			<li>在综合演示用例中保存用户时,异步发送通知消息邮件</li>
			<li>在servers/activemq目录演示优化过的activemq.xml配置文件</li>
		</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>