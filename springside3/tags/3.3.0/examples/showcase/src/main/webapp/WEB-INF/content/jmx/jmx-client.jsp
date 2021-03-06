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

		//系统配置(MBean代理)
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
				$('#updateMessage').text(data.message).show().fadeOut(2000);
			});
		}

		//Hibernate运行统计(直接读取属性/调用方法)
		//动态获取服务器最新状态,返回JSON对象.
		function resetStatistics() {
			$.get("jmx-client!resetStatistics.action?statisticsName=" + $('#statisticsName').val(), function(data) {
				updateStatistics();
			});
		}

		//动态调用JMX函数logsummary().
		function logSummary() {
			$.get("jmx-client!logSummary.action");
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
			<li>客户端演示MBean代理及直接反射读取属性两种方式调用MBean</li>
		</ul>

		<h2>用户故事：</h2>
		<div>
			使用JMX动态配置服务节点的系统变量与Log4J日志等级,并实时监控Hibernate运行统计。<br/>
			客户端可使用JConsole, 远程进程URL为 service:jmx:rmi:///jndi/rmi://localhost:1099/showcase
		</div>
		
		<div class="yui-g">
		<div class="yui-u first">
		<h2>系统配置(MBean代理)</h2>
		<form id="configForm">
			<table class="noborder">
				<tr>
					<td>服务器节点名:</td>
					<td><input id="nodeName" name="nodeName" value="${nodeName}"/></td>
				</tr>
				<tr>
					<td>是否发送通知邮件:</td>
					<td><s:radio id="notificationMailEnabled" name="notificationMailEnabled"
								 value="notificationMailEnabled" list="#{'true':'是', 'false':'否'}" theme="simple"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="保存配置" onclick="saveConfig();"/>&nbsp;&nbsp;
						<input type="button" value="刷新配置" onclick="updateConfig();"/>
						<span id="saveMessage"></span><span id="updateMessage"></span>
					</td>
				</tr>
			</table>
		</form>
		</div>

		<div class="yui-u">
			<h2>Hibernate运行统计(反射读取属性/调用方法)</h2>
			<div>
				打开数据库连接:${hibernateStatistics.sessionOpenCount}<br/>
				关闭数据库连接:${hibernateStatistics.sessionCloseCount}<br/>
				<input type="button" value="在日志打印Hibernate统计信息" onclick="logSummary();"/>
			</div>
		</div>
		</div>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>