<%@ page contentType="text/html;charset=GBK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>����ƽ̨</title>
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script type="text/javascript">
			var logonUser='${sessionScope.CurrentUser.userId}';
			$(document).ready(function() {
				framehight = document.documentElement.clientHeight-10;
				$("#framepage").attr("height",framehight);
			});
		</script>
	</head>
	<body>
		<iframe id="framepage" name="framepage" src="<%=framePath %>/framepage.jsp" width="100%" height="100%" scrolling="false"></iframe>
	</body>
</html>