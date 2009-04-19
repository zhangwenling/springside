<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>搜索引擎演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h2>搜索引擎演示</h2>
技术说明：同时演示Solr与Hibernate Search，满足不同情形的需求。
<ul>
<li>Solr--松耦合的独立的搜索引擎服务器集群。</li>
<li>Hibernate Search--搜索实体的数据库操作全部基于Hibernate Search时的选择。</li>
</ul>


<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>