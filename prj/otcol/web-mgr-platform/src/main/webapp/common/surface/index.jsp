<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>
		<title>资金统一结算系统</title>
	</head>
	<frameset rows="60,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=surfacePath %>/top.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset id=middle cols="185,10,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=serverPath %>/commonMenuList.cmn" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=surfacePath %>/mainSwitch.html" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes">
      <frame src="<%=surfacePath %>/index1.jsp" name="workspace" APPLICATION="yes">
		</frameset>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>