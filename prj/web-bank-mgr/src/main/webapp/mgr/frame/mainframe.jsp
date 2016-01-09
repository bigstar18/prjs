<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>交易管理系统</title>
		<script>
			/*
			var currentKeyCode = -1;
			function document.onkeydown() { // 本窗口的所有下属页面都必须含有本函数
				top.currentKeyCode = event.keyCode;
			}
			function checkLeave(){
				if(event.clientX<=0 || event.clientY<0) { 
					alert('关闭');
					window.location="<%=basePath %>/userLogOut/commonUserLogout.action";
				}else{
					alert('刷新');
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
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>