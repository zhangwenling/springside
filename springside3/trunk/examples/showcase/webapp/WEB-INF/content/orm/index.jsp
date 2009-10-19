<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>数据库访问高级演示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="main">
<h1>数据库访问高级演示</h1>
<h2>技术说明：</h2>
<ul>
	<li>Hibernate Event，在修改User对象时自动加入审计信息。(综合示例)</li>
	<li>Hibernate @Version字段，在Struts2 Prepareable Action里的使用。(综合示例)</li>
	<li>Hibernate Clob字段。(综合示例)</li>
	<li>Hibernate 批量更新操作的HQL。(测试用例)</li>
	<li>Hibernate Native SQL。(测试用例)</li>
	<li>Hibernate 查询时预加载Lazy Load关联对象。(测试用例)</li>
	<li>Hibernate 扩展Dialect，加入数据库特性语句。(测试用例)</li>
	<li>SQL执行记录及时间分析：基于P6Spy的通用实现。</li>
	<li>Hibernate Envers，自动记录关键业务对象的修改历史。(计划中)</li>
</ul>
<h4>用户故事：</h4>
<ul>
   <li>访问对象列表及修改用户对象，P6Spy将sql记录到c:/spy.log，使用irontracksql观看结果。</li>
</ul>

</div>
</div>
</body>
</html>