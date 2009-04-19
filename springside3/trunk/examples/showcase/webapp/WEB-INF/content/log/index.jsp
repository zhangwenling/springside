<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>日志高级演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h2>日志高级演示</h2>
技术说明：
<ul>
	<li>告警邮件发送：日志使用JMS Appender发送，消息接收者将日志发送到告警邮箱。</li>
	<li>日志文件集中记录：日志使用JMS Appender发送，消息接收者统一记录日志到中央服务器文件。</li>
	<li>日志数据库集中存储：日志使用JDBC Appender发送，统一记录日志到数据库。</li>
</ul>

<div id="footer"><a href="${ctx}/">返回首页</a></div>
</body>
</html>