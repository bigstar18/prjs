<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>���׹���ϵͳ</title>
	</head>
		<frameset id=middle cols="250,5,*" frameborder="NO" border="0" framespacing="0">
			<frame src='<%=basePath %>/category/breed/breedTree.action' name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=mgrPath %>/app/integrated/breed/shrinkbar.jsp" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes" id="mainSwitch">
      		<frame src="<%=mgrPath %>/app/integrated/breed/rightframe.jsp" name="workspace" APPLICATION="yes">
		</frameset>
	<noframes>
		<body>
			
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>
