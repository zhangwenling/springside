<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="leftbar">
    <h2>(部分)完成特性</h2>

    <div class="line">
        <a href="${ctx}/common/users.htm">综合演示</a>
        <a href="${ctx}/jmx/jmx-client.action">JMX演示</a>
		<a href="${ctx}/email/index.action">邮件演示</a>
		<a href="${ctx}/ajax/index.action">Ajax演示</a>
		<a href="${ctx}/report/index.action">报表演示</a>
		<a href="${ctx}/xml/index.action">XML操作演示</a>
		<a href="${ctx}/schedule/index.action">定时任务演示</a>
		<a href="${ctx}/security/index.action">安全高级演示</a>
		<a href="${ctx}/log/log.action">日志高级演示</a>
		<a href="${ctx}/web/index.action">Web高级演示</a>
		<a href="${ctx}/orm/index.action">数据库高级演示</a>
		<a href="${ctx}/webservice/index.action">Web服务高级演示</a>
		<a href="${ctx}/profiler/index.action">性能分析演示</a>
    </div>
    
    <h2>计划中特性</h2>

    <div class="line">
    	<a href="${ctx}/jms/index.action">JMS演示</a>
		<a href="${ctx}/search/index.action">搜索引擎演示</a>
		<a href="${ctx}/rule/index.action">规则引擎演示</a>
		<a href="${ctx}/workflow/index.action">工作流演示</a>
		<a href="${ctx}/cache/index.action">缓存高级演示</a>
    </div>
</div>