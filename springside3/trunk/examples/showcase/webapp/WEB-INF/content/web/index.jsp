<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Web高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
</head>
<body>
<%@ include file="/common/header.jsp"%>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="main">
<h1>Web高级演示</h1>
<h2>技术说明：</h2>
<ul>
	<li>URLRewrite, 使用URL Rewrite转换用户友好的URL. (综合示例)</li>
</ul>
<h2>用户故事：</h2>
<ul>
	<li>综合示例中的地址为搜索引擎友好的地址,如用户列表地址为users.htm, 用户1的修改地址为user/1.htm</li>
</ul>

</div>
</div>
</body>
</html>