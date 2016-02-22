<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.trade.bank.util.Tool"%>
<%
String msgExt = Tool.delNull(request.getParameter("msgExt"));
String respCode = Tool.delNull(request.getParameter("respCode"));

String payResult=Tool.delNull(request.getParameter("payResult"));
String errorCode=Tool.delNull(request.getParameter("errorCode"));

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
if("0000".equals(respCode)||"1".equals(payResult)){
%>
alert("操作成功");
<%
}else{
%>
alert("操作失败，返回信息:<%=msgExt+errorCode%>");
<%
}%>
window.close();
</SCRIPT>