<%@ page contentType="text/html;charset=GBK" %>
<%
	Object obj = request.getAttribute("javax.servlet.error.exception");
	String errorMsg = null;
	String originMsg = null;
	if(obj != null){
		originMsg = obj.toString();
		errorMsg = originMsg;
		errorMsg = errorMsg.replaceAll("&lt;","<");
		errorMsg = errorMsg.replaceAll("&lg;",">");
	} else {
		originMsg = request.getParameter("errorMsg");
		errorMsg = originMsg;
	}
%>
<html>
<head>
	<title>����ҳ��</title>
	<script language="javascript">
		alert('���ִ���<%=errorMsg%>');
	</script>
</head>
  
<body>
	���ִ���<br>
	<%=originMsg%><br>
</body>
</html>
