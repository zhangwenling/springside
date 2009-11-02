<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Web高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/content?path=css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>Web高级演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>URLRewrite, 使用URL Rewrite转换用户友好的URL.</li>
			<li>内容下载Servlet, 演示文件高效读取, 客户端缓存控制, 压缩传输, 弹出下载对话框等.</li>
		</ul>
		<h2>用户故事：</h2>
		<ul>
			<li>URLRewrite: 综合示例中的地址为搜索引擎友好的地址, 如用户列表地址为users.htm, 用户1的修改地址为user/1.htm</li>
			<li>内容下载Servlet:<br/>
			1.直接显示图片: <img src="${ctx}/content?path=img/logo.jpg"/><br/>
			2.转向图片服务器:<img src="${ctx}/content?path=img/logo.jpg&redirect=true"/><br/>
			3.<a href="${ctx}/content?path=img/logo.jpg&download=true">图片下载链接</a></li>
		</ul>

	</div>
</div>
</body>
</html>