<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日志高级演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h2>日志高级演示</h2>
<h4>技术说明：</h4>
<ul>
	<li>告警邮件发送：日志使用JMS Appender发送，消息接收者将日志发送到告警邮箱。</li>
	<li>日志文件集中记录：日志使用JMS Appender发送，消息接收者统一记录日志到中央服务器文件。</li>
</ul>
</div>
</div>
</body>
</html>