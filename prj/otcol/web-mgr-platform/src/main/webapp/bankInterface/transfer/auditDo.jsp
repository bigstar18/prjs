<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%

	long id = Long.parseLong(request.getParameter("id"));	
	int rst = Integer.parseInt(request.getParameter("rst"));	
	int moneyType = Integer.parseInt(request.getParameter("type"));
	
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	long result = 0;
	try{		
		result = cp.bankTransfer(id,rst,moneyType);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		result = -1;
	}

	String msg = "";
	if(result == 0)
	{
		msg = "提交审核成功！";
	}	
	else
	{
		msg = "提交审核失败！";
	}	

	%>
	<SCRIPT LANGUAGE="JavaScript">
		<!--
		alert('<%=msg %>');
		//window.opener.location.reload();
		window.returnValue="1";
		window.location = "auditList.jsp";
		//-->
		</SCRIPT>	
	<%
		

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>资金划转审核</title>
  </head>
  
    <SCRIPT LANGUAGE="JavaScript">

	//-->
	</SCRIPT>

  <body>

  </body>
</html>

