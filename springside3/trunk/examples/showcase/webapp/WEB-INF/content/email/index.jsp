<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>邮件演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h2>邮件演示</h2>
<p>技术说明：演示多线程群发简单文本邮件，和发送带附件的由Freemaker模板创建的HTML邮件。</p>
<p>用户故事：<br/>
在综合演示用例中，保存用户时，发送带附件的HTML通知邮件。<br/>
在Quartz示例中，每日定时器触发时，多线程群发邮件给将被修改的用户。<br/>
演示邮箱名为springside3.demo@gmail.com, 密码为demoforyou。</p>

<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>