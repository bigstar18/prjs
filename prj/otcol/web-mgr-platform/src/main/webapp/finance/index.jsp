<%@ page contentType="text/html;charset=GBK" %>
<html>
	<head>
	    <%@ include file="public/testLogon.jsp" %>
		<%@ include file="public/headInc.jsp" %>
		
		<title>财务系统--帐务管理子系统</title>
	</head>
	<frameset rows="55,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="top.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset cols="140,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=basePath%>/leftMenu.jsp" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=basePath%>/main.jsp" name="mainFrame" APPLICATION="yes" >
		</frameset>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！  
		</body>
	</noframes>
</html>