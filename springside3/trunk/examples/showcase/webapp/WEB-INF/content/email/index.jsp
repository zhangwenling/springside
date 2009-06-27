<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>邮件演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>邮件演示</h3>
<h4>技术说明：</h4>
<ul>
<li>简单文本邮件演示。</li>
<li>使用Freemarker模板创建HTML内容，并带附件的MIME邮件演示。</li>
</ul>
<h4>用户故事：</h4>
在综合演示用例中保存用户时，发送简单文本邮件和带附件的MIME邮件。<br/>
演示邮箱名为springside3.demo@gmail.com, 密码为demoforyou。

</div>
</div>
</body>
</html>