<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<%
String msgExt = request.getParameter("msgExt");
String respCode = request.getParameter("respCode");

%>
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=gbk">	
    <title>返回</title>
</head>
<body oncontextmenu="return false">
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<%
if(respCode.equals("0000")){
%>
alert("操作成功");
<%
}else{
%>
alert("操作失败，国付宝返回信息:<%=msgExt%>");
<%
}%>
window.close();
</SCRIPT>