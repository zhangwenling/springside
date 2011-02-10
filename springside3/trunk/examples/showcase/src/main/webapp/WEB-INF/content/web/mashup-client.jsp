<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>跨域名Mashup演示</title>
	<%@ include file="/common/meta.jsp" %>

	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		var remoteUrl = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";

		//使用JQuery.getJSON(url?callback=?)方式跨域名访问内容
		function fetchMashupContent() {
			$.getJSON(remoteUrl + "/web/mashup/mashup-server.action?callback=?", function(data) {
				$('#mashupContent').html(data.html);
				$('#mashupContent').show();
			});
		}
	</script>
</head>

<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last prepend-top">
		<h2>Mashup演示</h2>
	
		<h3>技术说明：</h3>
		<p>
			演示基于JQuery的JSONP实现，绕过浏览器对Ajax不能访问跨域名网站内容的限制实现Mashup.<br/>
			请先将本页另存为本地html文件，打开本地文件访问本地应用服务器中的内容即为跨域访问的场景.
		</p>
		<p><input type="button" value="获取内容" onclick="fetchMashupContent();"/></p>	
		<p>跨域页面内容:</p>
		<div id="mashupContent" style="display:none"/>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>