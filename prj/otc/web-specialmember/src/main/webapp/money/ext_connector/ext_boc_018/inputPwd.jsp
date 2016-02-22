<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.BOCCapitalProcessorRMI"%>
<base target="_self">
<%
	String contact = request.getParameter("contact");
	String id = request.getParameter("id");
	String flag = request.getParameter("flag");
%> 
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/report.css" type="text/css"/>
    <title>输入密码</title>
  </head>
<body>
<form name="frm" action="" method="post">
	<input type="hidden" name="contact" value="<%=contact%>">
	<input type="hidden" name="id" value="<%=id%>">
	<table>
		<tr>
			<td>请输入银行密码：</td>
			<td><input class="input_text" type="password" name="pwd" id="pwd" />&nbsp;&nbsp;</td>
			<!--td><button id="sub_btn" type="button" class="smlbtn" onclick="next();">下一步</button></td-->
			<td><input id="sub_btn" type="button" class="smlbtn" onclick="next();" value="下一步"/></td>
		</tr>
	</table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function next(){
	if(frm.pwd.value == ""){
		alert("请出入银行密码");
		return false;
	}
	var flag = '<%=flag%>';
	if(flag == 0){
		frm.action = "openAccount.jsp";
	}else if(flag == 1){
		frm.action = "delCorr.jsp";
	}
	frm.submit();
}	
</SCRIPT>