<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<%
	String commodityID2 = request.getParameter("commodityID2");
	pageContext.setAttribute("commodityID2",commodityID2);
%>
<frameset rows="30,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<c:url value="/timebargain/tradeMonitor/unTradeInfoT.jsp?commodityID2=${commodityID2}"/>" name="topFrame" noresize scrolling="NO" application="yes">
		<frameset cols="50%,50%" frameborder="NO" border="1" framespacing="1" bordercolor="#ffffff">
			<frame src="<c:url value="/timebargain/tradeMonitor/unTradeInfoB.jsp"/>" name="leftFrame" scrolling="NO" application="yes">
			<frame src="<c:url value="/timebargain/tradeMonitor/unTradeInfoS.jsp"/>" name="rightFrame"  noresize scrolling="NO" application="yes">
		</frameset>
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>