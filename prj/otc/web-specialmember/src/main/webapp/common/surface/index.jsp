<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/commonLogon.jsp"%>
<html>
	<head>
		<title>长三角商品交易所(模拟)</title>
		<link rel="shortcut icon" href="<%=basePath%>/favicon.ico">
        <link rel="Bookmark" href="<%=basePath%>/favicon.ico">	
<script>
　　　/*function checkLeave(){
          window.location="<%=basePath %>/userLogOut/commonUserLogout.action";
　　　}
   window.onbeforeunload=checkLeave;*/
</script>
	</head>
	<frameset rows="65,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=surfacePath %>/top.jsp?AUsessionId=${LOGINIDS}" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset id=middle cols="185,10,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=basePath %>/menu/commonMenuList.action?AUsessionId=${LOGINIDS}" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=surfacePath %>/mainSwitch.html" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes">
      <frame src="<%=surfacePath %>/index1.jsp?AUsessionId=${LOGINIDS}" name="workspace" APPLICATION="yes">
		</frameset>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>