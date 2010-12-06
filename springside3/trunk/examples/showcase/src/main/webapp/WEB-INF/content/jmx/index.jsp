<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMX演示用例</title>
	<%@ include file="/common/meta.jsp" %>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">

		//系统配置(MBean代理)//
		//动态提交表单保存服务器配置.
		function saveConfig() {
			$.get("jmx-client!saveConfig.action?" + $("form").serialize(), function(data) {
				$('#saveMessage').text(data).show().fadeOut(2000);
			});
		}

		//动态获取服务器最新配置,返回JSON对象.
		function refreshConfig() {
			$.getJSON("jmx-client!refreshConfig.action", function(data) {
				$('#nodeName').val(data.nodeName);
				$('input:radio[name=notificationMailEnabled]').val([data.notificationMailEnabled]);
				$('#refreshMessage').text(data.message).show().fadeOut(2000);
			});
		}

		//Trace控制(直接读取属性/调用方法)//
		//动态调用JMX函数start/stopTrace().
		function startTrace() {
			$.get("jmx-client!startTrace.action");
			$('#traceStatus').text("true");
		}

		function stopTrace() {
			$.get("jmx-client!stopTrace.action");
			$('#traceStatus').text("false");
		}
	</script>
</head>

<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>JMX演示用例</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>服务端演示使用Spring annotation定义MBean</li>
		</ul>

		<h2>用户故事：</h2>
		<div>
			使用JMX动态配置查看和配置Log4J日志等级。<br/>
			客户端可使用JConsole或JManager, 远程进程URL为 localhost:2099 或完整版的service:jmx:rmi:///jndi/rmi://localhost:2099/jmxrmi
		</div>
		
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>