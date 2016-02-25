<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="session.jsp"%>
<html>
	<head>	
		<META http-equiv="Content-Type" content="text/html; charset=GBK">	
		<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
		<title>银行接口后台管理系统</title>
	</head>
	<frameset rows="55,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="top.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset cols="140,*" frameborder="NO" border="0" framespacing="0">
			<frame src="leftMenu.jsp" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="main.html" name="mainFrame" APPLICATION="yes">
		</frameset>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>