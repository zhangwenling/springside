<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Ajax演示</title>
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
		<h1>Ajax演示</h1>
		<ul>
			<li><a href="${ctx}/ajax/mashup/mashup-client.action">跨域名Mashup演示</a> 演示基于JSONP Mashup 跨域名网站的内容.</li>
		</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>

</html>