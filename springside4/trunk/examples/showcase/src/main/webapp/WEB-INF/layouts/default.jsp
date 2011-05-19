<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Showcase示例:<sitemesh:write property='title' /></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${ctx}/css/style-min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection" />
<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print" />
<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->

<sitemesh:write property='head' />
</head>

<body>
	<div class="container">
		<%@ include file="/common/header.jsp"%>
		<div id="content">
			<%@ include file="/common/left.jsp"%>
			<div class="span-18 last prepend-top">
				<sitemesh:write property='body' />
			</div>
		</div>
		<%@ include file="/common/footer.jsp"%>
	</div>
</body>
</html>