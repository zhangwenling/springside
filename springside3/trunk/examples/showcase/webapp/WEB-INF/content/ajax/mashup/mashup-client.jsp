<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>跨域Mashup演示</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
    	var remoteUrl ="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";	

		//使用JQuery.getJSON(url?callback=?)方式跨域访问内容
		function getMashupContent() {
			$.getJSON(remoteUrl+"/ajax/mashup/mashup-server.action?callback=?",function(data){
				$('#mashupContnt').html(data.content);
			});
		}
	</script>
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
	<h2>Mashup演示</h2>
	<h4>技术说明：</h4>
	演示基于JQuery，绕过浏览器对Ajax访问跨域名网站内容的限制。<br/>
	 请先将本页另存为本地html文件，打开本地文件访问应用服务器即为跨域访问的场景。

	<p><input type="button" value="获取内容" onclick="getMashupContent();" /></p>

	<p>跨域页面内容：</p>

	<div id="mashupContnt"></div>
</div>
</div>
</body>
</html>