<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@include file="session.jsp"%>
<html>
	<head>	
		<META http-equiv="Content-Type" content="text/html; charset=gb2312">	
		<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
		<title>出入金登录</title>
	</head>
	<frameset rows="55,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="menu.jsp" name="menu" scrolling="NO" noresize APPLICATION="yes">
		<frame src="Money.jsp" name="list" scrolling="YES" noresize APPLICATION="yes">
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>