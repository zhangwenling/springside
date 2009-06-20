<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>数据库访问高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>数据库访问高级演示</h3>
<h4>技术说明：</h4>
<ul>
	<li>Hibernate Event，在综合示例中修改User对象时自动加入审计信息。</li>
	<li>Hibernate @Version字段，在综合示例中演示在Struts2 Prepareable Action里的使用。</li>
	<li>Hibernate Dialect，演示扩展Dielect，加入数据库特定特性。</li>
	<li>P6spy，在c:/spy.log中记录所执行的sql。</li>
	<li>Hibernate Native SQL，演示预加载已设为@Lazy的关联对象。(计划中,等Hibernte 3.3.2发布修复多对多查询的bug)</li>
	<li>Hibernate Envers，自动记录关键业务对象的修改历史。(计划中)</li>
</ul>
</div>
</div>
</body>
</html>