<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	<link href="${ctx}/css/style-min.css" type="text/css" rel="stylesheet"/>
<title>Showcase 示例</title>
</head>
<body>
<div class="container">
	<%@ include	file="/common/header.jsp"%>
	<div id="content">
		<%@ include file="/common/left.jsp"%>
		<div class="span-18 last prepend-top">
			<p>各式主流的、实用的、好玩的开源项目大派对。</p>
			<p>SpringSide 4.0新张大吉,新增演示：</p>
			<ul>
				<li>MongoDB演示</li>
			</ul>
		</div>
	</div>
	<%@ include file="/common/footer.jsp"%>
</div>
</body>
</html>