<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>
	    		<link rel="shortcut icon" href="<%=basePath%>/favicon.ico">
        <link rel="Bookmark" href="<%=basePath%>/favicon.ico">	
		<title>长三角商品交易所(模拟)</title>
<script>
    /*  var currentKeyCode = -1;
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
   window.onbeforeunload=checkLeave;*/
</script>
	</head>
	<frameset rows="65,*" cols="*" frameborder="NO" border="0" framespacing="0">
		<frame src="<%=surfacePath %>/top.jsp?AUsessionId=${LOGINIDS}" name="topFrame" scrolling="NO" noresize APPLICATION="yes">
		<frameset id=middle cols="185,10,*" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=basePath %>/menu/commonMenuList.action?AUsessionId=${LOGINIDS}" name="leftFrame" scrolling="NO" noresize APPLICATION="yes">
			<frame src="<%=surfacePath %>/mainSwitch.html?AUsessionId=${LOGINIDS}" name="mainSwitch" scrolling="NO" noresize APPLICATION="yes" id="mainSwitch">
      		<frame src="<%=surfacePath %>/index1.jsp?AUsessionId=${LOGINIDS}" name="workspace" APPLICATION="yes">
		</frameset>
	</frameset>
	<noframes>
		<body>
			对不起，您的浏览器不支持框架集！ 
		</body>
	</noframes>
</html>