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
	<li>Hibernate Event演示，在综合演示示例中自动在修改User对象时加入审计信息。</li>
	<li>Hibernate @Version字段在综合演示示例Struts2 Prepareable Action中的使用。</li>
	<li>Hibernate Envers演示，自动记录对象的修改历史。(计划中,等待Hibernate 3.5)</li>
</ul>
</div>
</div>
</body>
</html>