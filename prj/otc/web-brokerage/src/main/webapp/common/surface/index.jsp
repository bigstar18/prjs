<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>
		<title>��������Ʒ������(ģ��)</title>
	</head>
	<frameset rows="65,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=surfacePath %>/top.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset id=middle cols="185,10,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=basePath %>/common/surface/leftMenu.jsp" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=surfacePath %>/mainSwitch.html" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes">
      <frame src="<%=surfacePath %>/index1.jsp" name="workspace" APPLICATION="yes">
		</frameset>
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>