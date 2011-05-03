<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>XML操作演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	
	<link href="${ctx}/css/style-min.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
</head>
<body>
<div class="container">
<%@ include file="/common/header.jsp" %>
<div id="content">
	<%@ include file="/common/left.jsp" %>
	<div class="span-18 last prepend-top">
		<h2>XML/JSON操作演示</h2>

		<h3>技术说明：</h3>
		<ul>
			<li>基于JAXB2.0的Java-XML绑定, 演示根元素是List, 属性是List与Map等特殊情况. 见JaxbTest</li>
			<li>万能老倌Dom4j, 见JaxbTest.</li>
			<li>最快的JSON转换类Jackson, 见JacksonTest.</li>
		</ul>

		<h3>用户故事：</h3>
		<p>在JaxbDemo.java中演示XML与Java对象的转换及Dom4j的使用.</p>
		<p>在JsonDemo.java测试用例中演示JSON字符串与Bean, Map, List<String>, List<Bean>, Bean[],Enum之间的转换.</p>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>