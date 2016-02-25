<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%

	String payBankId = request.getParameter("payBankId");	
	String recBankId = request.getParameter("recBankId");	
	String amount = request.getParameter("amount");
    String moneyType = request.getParameter("type");
	String trandType = request.getParameter("trandType");
	String postscript = Tool.delNull(request.getParameter("postscript"));
	BankTransferValue bankTransferValue = new BankTransferValue();
	bankTransferValue.payBankId = payBankId;
	bankTransferValue.recBankId = recBankId;
	bankTransferValue.money = Double.parseDouble(amount);
	bankTransferValue.note = postscript;
	bankTransferValue.type = Integer.valueOf(moneyType);
	bankTransferValue.transType = trandType;
	bankTransferValue.status = 2;

	BankDAO dao = BankDAOFactory.getDAO();

	long result = 0;
	try{		
		result = dao.addBankTransfer(bankTransferValue);
		System.out.println(result);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		result = -1;
	}
    
	String msg = "";
	if(result > 0)
	{
		msg = "提交银行间资金划转申请成功！";
	}	
	else
	{
		msg = "提交银行间资金划转申请失败！";
	}	

	%>
	<SCRIPT LANGUAGE="JavaScript">
		<!--
		alert('<%=msg %>');
		//window.opener.location.reload();
		window.returnValue="1";
		window.location = "transferList.jsp";
		//-->
		</SCRIPT>	
	<%
		

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>资金划转</title>
  </head>
  
    <SCRIPT LANGUAGE="JavaScript">

	//-->
	</SCRIPT>

  <body>

  </body>
</html>

