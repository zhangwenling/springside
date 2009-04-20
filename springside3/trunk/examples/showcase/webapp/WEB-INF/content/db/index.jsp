<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>数据库访问高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/default.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h2>数据库访问高级演示</h2>
<p>技术说明：Hibernate Event演示，在综合演示示例中自动在修改User对象时加入审计信息.</p>
<p>未来版本：Hibernate envers，自动记录对象的修改历史。(等待Hibernate 3.5)</p>

<div id="footer">
	<a href="${ctx}/">返回首页</a>
</div>
</body>
</html>