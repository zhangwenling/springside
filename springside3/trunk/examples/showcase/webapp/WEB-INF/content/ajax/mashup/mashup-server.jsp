<%@ page contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.json.JSONObject"%>
<%
	//获取JQuery动态生成callback函数名
	String callbackFunction = request.getParameter("callback");

	//生成返回的html内容
	String content = "<p>Hello World!</p>";

	//构造作为callback函数参数的json对象
	String jsonParam = new JSONObject().put("content", content).toString();

	//最后输出内容为一句javascript方法调用，类似于 jsonp1232617({"content" : "<p>Hello World!</p>"});
%>
<%=callbackFunction%>(<%=jsonParam%>);
