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
		<frame src="<c:url value="/timebargain/tradeMonitor/commodityMonitorT.jsp?commodityID2=${commodityID2}"/>" name="topFrame" noresize scrolling="NO" application="yes">
		<frameset cols="33%,33%,34%" frameborder="NO" border="1" framespacing="1" bordercolor="#ffffff">
			<frame src="<c:url value="/timebargain/tradeMonitor/commodityMonitorB.jsp"/>" name="leftFrame" scrolling="auto" application="yes">
			<frame src="<c:url value="/timebargain/tradeMonitor/commodityMonitorS.jsp"/>" name="middleFrame"  noresize scrolling="auto" application="yes">
			<frame src="<c:url value="/timebargain/tradeMonitor/commodityMonitorQ.jsp"/>" name="rightFrame"  noresize scrolling="auto" application="yes">
		</frameset>
	</frameset>
<body bgcolor="#ffffff" >
</body>
</html>