<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>���׹���ϵͳ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript" src="${publicPath}/js/jquery-1.6.min.js"></script>
		<script>
			//�����������frame
			var mainFrame;
			//��ȡ�����������frame
			function getMainFrame(){
				if(!mainFrame){
					mainFrame = $(document);
				}
				return mainFrame;
			}
			
		</script>
	</head>
	<frameset rows="65,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=framePath%>/commonmenu/topframe.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset id=middle cols="185,10,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=basePath %>/menu/menuList.action" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=framePath %>/shrinkbar.jsp" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes" id="mainSwitch">
      		<frame src="<%=framePath %>/rightframe.jsp" name="workspace" APPLICATION="yes">
		</frameset>
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>