<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>财务结算</title>
	</head>
	<frameset rows="5%,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="financeBalance.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes" id="topFrame">
		<frame src="${basePath}/finance/clearStatus/clearStatusList.action" id="mainFrame" name="mainFrame" style="margin-top: 0px;">
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>