<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title><sitemesh:write property='title'/></title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
    <link href="${ctx}/static/mini-web.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/static/jquery/jquery-validation/1.8.0/milk.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/static/blueprint/1.0.1/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/static/blueprint/1.0.1/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/static/blueprint/1.0.1/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->

	<script src="${ctx}/static/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery/jquery-validation/1.8.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery/jquery-validation/1.8.0/messages_cn.js" type="text/javascript"></script>
    
    <sitemesh:write property='head'/>
  </head>
  
  <body>
    <div class="container">
		<%@ include file="/common/header.jsp" %>
		<div id="content">
			<div class="span-24 last">
	    		<sitemesh:write property='body'/>
	    	</div>
			<%@ include file="/common/footer.jsp" %>
		</div>
  	</div>	
  </body>
</html>