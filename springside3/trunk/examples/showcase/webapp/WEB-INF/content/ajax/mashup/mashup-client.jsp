<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mashup演示用例</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet">
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
    	var crossDomainUrl ="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";	

		//使用JQuery.getJSON(url?callback=?)方式跨域访问内容
		function getMashupContent() {
			$.getJSON(crossDomainUrl+"/ajax/mashup/mashup-server.action?callback=?",function(data){
				$('#mashupContnt').html(data.html);
			});
		}
	</script>
</head>
<body>
	<h3>Mashup演示用例</h3>
	<p>请先将本页另存为本地html文件, 打开本地文件访问应用服务器即为跨域访问的场景</p>

	<input type="button" value="获取内容" onclick="getMashupContent();" />

	<p>跨域页面内容:</p>

	<div id="mashupContnt"></div>

	<p><a href="${ctx}/">返回首页</a></p>
</body>
</html>