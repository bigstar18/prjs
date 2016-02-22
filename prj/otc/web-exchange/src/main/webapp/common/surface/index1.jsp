<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="shortcut icon" href="<%=basePath%>/favicon.ico">
        <link rel="Bookmark" href="<%=basePath%>/favicon.ico">	
	</head>
			<frameset rows="30,*" cols="*" framespacing="0" frameborder="NO" border="0" id="workspace" name="workspace">
      <frame name="topFrame1" src="top1.jsp?AUsessionId=${LOGINIDS}" scrolling="NO" noresize APPLICATION="yes">
      <frame name="mainFrame" src="main.jsp?AUsessionId=${LOGINIDS}" APPLICATION="yes">
      </frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>