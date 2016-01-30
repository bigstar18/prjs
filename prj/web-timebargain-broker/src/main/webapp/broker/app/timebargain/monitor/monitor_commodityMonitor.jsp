<%@ page pageEncoding="GBK" %>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>ÉÌÆ·¶©»õ¼à¿Ø</title>
</head>
<frameset rows="30,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="${mgrPath}/app/timebargain/monitor/monitor_commodityMonitorT.jsp" name="topFrame" noresize scrolling="NO" application="yes">
		<frameset cols="33%,33%,34%" frameborder="NO" border="1" framespacing="1" bordercolor="#ffffff">
			<frame src="${mgrPath}/app/timebargain/monitor/monitor_commodityMonitorB.jsp" name="leftFrame" scrolling="auto" application="yes">
			<frame src="${mgrPath}/app/timebargain/monitor/monitor_commodityMonitorS.jsp" name="middleFrame"  noresize scrolling="auto" application="yes">
			<frame src="${mgrPath}/app/timebargain/monitor/monitor_commodityMonitorQ.jsp" name="rightFrame"  noresize scrolling="auto" application="yes">
		</frameset>
	</frameset>
<body bgcolor="#ffffff" >
</body>
</html>