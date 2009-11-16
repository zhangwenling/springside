<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>XML操作演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div id="main">
		<h1>XML操作演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>基于JAXB2.0的Java-XML绑定, 演示根元素是List, 属性是List与Map等特殊情况.</li>
			<li>万能老倌Dom4j.</li>
		</ul>

		<h2>用户故事：</h2>
		<p>在JaxbTest测试用例中演示XML与Java对象的转换及Dom4j的使用.</p>
	</div>
</div>
</body>
</html>