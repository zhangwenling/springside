<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>性能分析演示</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/css/main.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div id="content">
<%@ include file="/common/left.jsp"%>
<div id="mainbar">
<h3>性能分析演示</h3>
<h4>技术说明：</h4>
<ul>
	<li>程序执行时间白盒分析：基于Perf4j记录任意方法与代码段的执行时间。</li>
	<li>SQL执行记录及时间分析：基于P6Spy的通用实现。</li>
</ul>

<h4>用户故事：</h4>简单的定时在Console打印当前用户数量。
<ul>
   <li>访问对象列表及修改用户对象，Per4j将代码执行时间记录到c:/perf4j.log，执行bin/perf4j.bat产生报表。</li>
   <li>访问对象列表及修改用户对象，P6Spy将sql记录到c:/spy.log，使用irontracksql观看结果。</li>
</ul>

</div>
</div>
</body>
</html>