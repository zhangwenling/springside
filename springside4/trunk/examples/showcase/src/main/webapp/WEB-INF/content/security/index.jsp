<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>安全高级演示</title>
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
	<div class="span-18 last prepend-top">
		<h2>SpringSecurity高级演示</h2>
		<ul>
			<li>SpringSecurity 与 CXF WS-Security集成并控制方法安全演示.(见CXF相关演示)</li>
			<li>SpringSecurity 与 Jersey Http-Basic集成并控制方法安全演示.(见Jersey相关演示)</li>
			<li>SpringSecurity Sha1散列密码与同时登录人数限制配置以及增加UserDetails属性的演示.</li>
			<li>SpringSecuirty 开发时自动登录用户的Filter演示.</li>
		</ul>
		
		<h2>安全高级演示</h2>
		<ul>
			<li>SHA-1与MD5消息摘要(见DigestUtils及其测试用例)</li>
			<li>HMAC-SHA1共享密钥消息签名演示.(见CryptoUtils及其测试用例)</li>
			<li>DES及其替代者AES的共享密钥消息加密演示.(见CryptoUtils及其测试用例)</li>
		</ul>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>