<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JMX演示用例</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet">
	<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript">
		//动态提交表单保存服务器配置.
		function saveConfig(){
			$.get("jmx-client!saveConfig.action?" + $("form").serialize(), function(data){
				$('#saveMessage').text(data).show().fadeOut(2000);
			});
		}

		//动态获取服务器最新配置,返回JSON对象.
		function updateConfig(){
			$.getJSON("jmx-client!updateConfig.action", function(data){
				$('#nodeName').val(data.nodeName);
				$('input:radio[name=statisticsEnabled]').val([data.statisticsEnabled]);
				$('#updateMessage').text(data.message).show().fadeOut(2000);
			});
		}
	
		//动态获取服务器最新状态,返回普通文本.
		function updateStatics(){
			$.get("jmx-client!updateStatics.action", function(data) {
				$('#queryUsersCount').val(data);
			});
		}
	</script>
</head>
<body>
	<h3>JMX & Ajax演示用例</h3>
	<p>说明：演示JMX配置系统变量并监控系统运行状态，同时演示基于JQuery的标准Ajax应用。<br/>
	亦可使用JConsole访问JMX Server, URL为 service:jmx:rmi:///jndi/rmi://localhost:1099/showcase<br/></p>
	<h4>服务器配置</h4>
	<form id="configForm">
	<table>
		<tr>
			<td>服务器节点名:</td>
			<td><input id="nodeName" name="nodeName" value="${nodeName}" /></td>
		</tr>
		<tr>
			<td>是否收集统计信息:</td>
			<td><s:radio id="statisticsEnabled" name="statisticsEnabled" value="statisticsEnabled" list="#{'true':'是', 'false':'否'}" theme="simple"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="保存配置" onclick="saveConfig();" />&nbsp;&nbsp;
				<input type="button" value="刷新配置" onclick="updateConfig();" />
				<span id="saveMessage"></span><span id="updateMessage"></span>
			</td>
		</tr>
	</table>
	</form>

	<h4>服务器状态</h4>
	<table class="inputView">
		<tr>
			<td>查询用户列表次数:</td>
			<td>
				<input name="queryUsersCount" id="queryUsersCount" value="${queryUsersCount}" readonly="readonly"/> 
				<input type="button" value="刷新状态" onclick="updateStatics();" />
			</td>
		</tr>
	</table>

	<p><a href="#/" target="_blank">打开新窗口并行操作</a>、<a href="${ctx}/">返回首页</a></p>
</body>
</html>