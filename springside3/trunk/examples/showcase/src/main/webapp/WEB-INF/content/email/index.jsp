<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>邮件演示</title>
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
		<h1>邮件演示</h1>

		<h2>技术说明:</h2>
		<ul>
			<li>简单文本邮件演示.</li>
			<li>带附件的MIME邮件演示, 使用Freemarker模板创建HTML内容.</li>
		</ul>
		
		<h2>用户故事：</h2>
		<p>
			在综合演示用例中保存用户时，发送两种邮件.<br/>
			演示邮箱名为springside3.demo@gmail.com, 密码为demoforyou.
		</p>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>