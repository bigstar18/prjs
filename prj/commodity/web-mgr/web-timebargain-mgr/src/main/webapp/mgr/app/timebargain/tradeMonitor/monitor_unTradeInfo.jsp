<%@ page pageEncoding="GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>未成交委托队列</title>
</head>
<frameset rows="30,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="${mgrPath}/app/timebargain/tradeMonitor/monitor_unTradeInfoT.jsp" name="topFrame" noresize scrolling="NO" application="yes">
		<frameset cols="50%,50%" frameborder="NO" border="1" framespacing="1" bordercolor="#ffffff">
			<frame src="${mgrPath}/app/timebargain/tradeMonitor/monitor_unTradeInfoB.jsp" name="leftFrame" scrolling="NO" application="yes">
			<frame src="${mgrPath}/app/timebargain/tradeMonitor/monitor_unTradeInfoS.jsp" name="rightFrame"  noresize scrolling="NO" application="yes">
		</frameset>
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>