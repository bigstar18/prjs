<%@ page pageEncoding="GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<title>未成交内存队列</title>
<script type="text/javascript">
function load(){
	//alert(document.getElementById("commodityID2").innerHTML);
}
</script>
</head>
<frameset rows="30,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="${mgrPath}/app/timebargain/tradeMonitor/memoryMonitor/monitor_saleQueueT.jsp" name="topFrame" noresize scrolling="NO" application="yes">
		<frameset cols="50%,50%" frameborder="NO" border="1" framespacing="1" bordercolor="#ffffff">
			<frame src="${mgrPath}/app/timebargain/tradeMonitor/memoryMonitor/monitor_saleQueueB.jsp" name="leftFrame" scrolling="NO" application="yes">
			<frame src="${mgrPath}/app/timebargain/tradeMonitor/memoryMonitor/monitor_saleQueueS.jsp" name="rightFrame"  noresize scrolling="NO" application="yes">
		</frameset>
</frameset>
<body bgcolor="#ffffff"  onload="return load();">

</body>
</html>