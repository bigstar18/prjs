<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<html>
	<head>
	    		<link rel="shortcut icon" href="<%=basePath%>/favicon.ico">
        <link rel="Bookmark" href="<%=basePath%>/favicon.ico">	
		<title>��������Ʒ������(ģ��)</title>
<script>
    /*  var currentKeyCode = -1;
    function document.onkeydown() { // �����ڵ���������ҳ�涼���뺬�б�����

       top.currentKeyCode = event.keyCode;
    }
    
������function checkLeave(){
	      if(event.clientX<=0 || event.clientY<0) { 
        	alert('�ر�');
        	window.location="<%=basePath %>/userLogOut/commonUserLogout.action";
        }else{
        	 alert('ˢ��');
        }
          
������}
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
			�Բ��������������֧�ֿ�ܼ��� 
		</body>
	</noframes>
</html>