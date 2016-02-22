<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>
	    <link rel="shortcut icon" href="<%=basePath%>/favicon.ico">
        <link rel="Bookmark" href="<%=basePath%>/favicon.ico">	
		<title></title>
	</head>
			<frameset rows="30,*" cols="*" framespacing="0" frameborder="NO" border="0" id="workspace" name="workspace">
      <frame name="topFrame1" src="top1.jsp" scrolling="NO" noresize APPLICATION="yes">
      <frame name="mainFrame" src="main.jsp" APPLICATION="yes">
      </frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>