<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>���׹���ϵͳ</title>
		<script>
			/*
			var currentKeyCode = -1;
			function document.onkeydown() { // �����ڵ���������ҳ�涼���뺬�б�����
				top.currentKeyCode = event.keyCode;
			}
			function checkLeave(){
				if(event.clientX<=0 || event.clientY<0) { 
					alert('�ر�');
					window.location="<%=basePath %>/userLogOut/commonUserLogout.action";
				}else{
					alert('ˢ��');
				}
			}
			window.onbeforeunload=checkLeave;
			*/
		</script>
	</head>
	<frameset rows="65,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=framePath %>/topframe.jsp" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frame src="mainframe_nohead.jsp" id=main>
	</frameset>
	<noframes>
		<body>
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>