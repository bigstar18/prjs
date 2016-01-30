<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易管理系统</title>
	</head>
		<frameset id=middle cols="250,5,*" frameborder="NO" border="0" framespacing="0">
			<frame src='<%=basePath %>/category/commodity/categoryTree.action' name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=mgrPath %>/app/integrated/category/shrinkbar.jsp" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes" id="mainSwitch">
      		<frame src="<%=mgrPath %>/app/integrated/category/rightframe.jsp" name="workspace" APPLICATION="yes">
		</frameset>
	<noframes>
		<body>
			
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>
