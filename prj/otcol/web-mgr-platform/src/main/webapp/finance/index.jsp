<%@ page contentType="text/html;charset=GBK" %>
<html>
	<head>
	    <%@ include file="public/testLogon.jsp" %>
		<%@ include file="public/headInc.jsp" %>
		
		<title>����ϵͳ--���������ϵͳ</title>
	</head>
	<frameset rows="55,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="top.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset cols="140,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=basePath%>/leftMenu.jsp" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=basePath%>/main.jsp" name="mainFrame" APPLICATION="yes" >
		</frameset>
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ���  
		</body>
	</noframes>
</html>