<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="org.springside.modules.utils.EncodeUtils" %>

<%
   String remoteImageUrl = "http://"+request.getServerName()+":"+request.getServerPort()+"/showcase/img/logo.jpg";
   String encodedImageUrl = EncodeUtils.urlEncode(remoteImageUrl);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Web高级演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href="${ctx}/css/yui.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/content?contentPath=css/style.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div id="doc3" class="yui-t2">
<%@ include file="/common/header.jsp" %>
<div id="bd">
	<%@ include file="/common/left.jsp" %>
	<div id="yui-main">
		<div class="yui-b">
		<h1>Web高级演示</h1>

		<h2>技术说明：</h2>
		<ul>
			<li>URLRewrite, 使用URL Rewrite转换用户友好的URL.</li>
			<li>高性能Web2.0网站:<br/>
			    1. 内容Servlet, 演示静态内容高效读取, 客户端缓存控制, 压缩传输, 弹出下载对话框.<br/>
			 	2. Http Client, 多线程高效获取远程Web内容.<br/>
			    3. ResponseHeaderFilter为静态内容添加Cache-Control Header<br/>
			    4. YUI Compressor 压缩js/css<br/>
			    5. Tomcat gzip 配置压缩传输大于2K的MimeType为html/xml/css/js的文本<br/> 
			</li>
		</ul>
		
		<h2>用户故事：</h2>
		<ul>
			<li>URLRewrite: 综合示例中的地址为搜索引擎友好的地址, 如用户列表地址为users.htm, 用户1的修改地址为user/1.htm</li>
			<li>内容下载Servlet:<img src="${ctx}/content?contentPath=img/logo.jpg"/> <a href="${ctx}/content?contentPath=img/logo.jpg&download=true">图片下载链接</a></li>
			<li>Http Client远程下载内容:<img src="${ctx}/remote-content?remoteUrl=<%=encodedImageUrl%>"/></li>
			<li>ResponseHeaderFilter使用见webapp中的web.xml</li>
			<li>YUI Compressor见bin/yuicompressor.bat命令及webapp中两个版本的js/css文件.</li>
			<li>Tomcat 压缩传输见/tools/tomcat/profiles/production中server.xml.</li>
		</ul>
		</div>
	</div>
</div>
<%@ include file="/common/footer.jsp" %>
</div>
</body>
</html>