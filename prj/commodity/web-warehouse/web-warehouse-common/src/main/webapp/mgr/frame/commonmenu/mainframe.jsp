<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>管理平台</title>
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script type="text/javascript">
			var logonUser='${sessionScope.CurrentUser.userId}';
		</script>
		<script type="text/javascript" src="${basePath}/mgr/public/js/jquerymessage/Messagedialog.js"></script>
		<script type="text/javascript" src="${basePath}/mgr/public/js/jms/amq_jquery_adapter.js"></script>
		<script type="text/javascript" src="${basePath}/mgr/public/js/jms/amq.js"></script>
		<script type="text/javascript" src="${basePath}/mgr/public/js/jquerymessage/showmessage.js"></script>
	</head>
	<body>
		<iframe id="framepage" name="framepage" src="<%=framePath %>/commonmenu/framepage.jsp" width="100%" height="100%" scrolling="false"></iframe>
	</body>
</html>