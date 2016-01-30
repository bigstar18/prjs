<%@ page contentType="text/html;charset=GBK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>管理平台</title>
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script type="text/javascript">
			var logonUser='${sessionScope.CurrentUser.userId}';
			$(document).ready(function() {
				framehight = document.documentElement.clientHeight-10;
				$("#framepage").attr("height",framehight);
			});
		</script>
		<script type="text/javascript" src="${basePath}/broker/public/js/jquerymessage/Messagedialog.js"></script>
		<script type="text/javascript" src="${basePath}/broker/public/js/jms/amq_jquery_adapter.js"></script>
		<script type="text/javascript" src="${basePath}/broker/public/js/jms/amq.js"></script>
		<script type="text/javascript" src="${basePath}/broker/public/js/jquerymessage/showmessage.js"></script>
	</head>
	<body>
		<iframe id="framepage" name="framepage" src="<%=framePath %>/framepage.jsp" width="100%" height="100%" scrolling="false"></iframe>
	</body>
</html>