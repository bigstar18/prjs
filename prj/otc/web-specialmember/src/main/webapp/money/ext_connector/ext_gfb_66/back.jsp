<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%
String msgExt = request.getParameter("msgExt");
String respCode = request.getParameter("respCode");

%>
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=gbk">	
    <title>����</title>
</head>
<body oncontextmenu="return false">
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<%
if(respCode.equals("0000")){
%>
alert("�����ɹ�");
<%
}else{
%>
alert("����ʧ�ܣ�������������Ϣ:<%=msgExt%>");
<%
}%>
window.close();
</SCRIPT>