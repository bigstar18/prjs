<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%

	String payBankId = request.getParameter("payBankId");	
	String recBankId = request.getParameter("recBankId");	
	String typeMoney = request.getParameter("type");
	String trandType = request.getParameter("trandType");
	String amount = request.getParameter("amount");
	String postscript = Tool.delNull(request.getParameter("postscript"));	
	String recFirmId = Tool.delNull(request.getParameter("recFirmId"));	
	String payAccount = "";
	String recAccount = "";
	String payAccountName = "";
	String recAccountName = "";

	BankDAO dao = BankDAOFactory.getDAO();
	Vector<DicValue> list = dao.getDicList(" where type = 2");

	for (int i = 0; i < list.size(); i++) {
		DicValue val = (DicValue) list.get(i);
		//System.out.println("val.bankID=="+val.bankID);
		//System.out.println("payBankId=="+payBankId);
		if (val.bankID.equals(payBankId)&&val.name.equals("marketAccount")) {
			payAccount = val.value;
		} 
		if (val.bankID.equals(recBankId)&&val.name.equals("marketAccount")) {
			recAccount = val.value;
		} 
		if (val.bankID.equals(payBankId)&&val.name.equals("accountName")) {
			payAccountName = val.value;
		} 
		if (val.bankID.equals(recBankId)&&val.name.equals("accountName")) {
			recAccountName = val.value;
		}
	}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>资金划转</title>
  </head>
  
    <SCRIPT LANGUAGE="JavaScript">
	<!--
	function goback(){
		frm.action = "transfer.jsp";
		frm.submit();
	}
	function dosubmit(){
		frm.submit();
	}
	//-->
	</SCRIPT>

  <body>
  	<form id="frm" name="frm" action="confirmdo.jsp" method="post">
		<input type='hidden' name='payBankId' value='<%=payBankId%>'>
		<input type='hidden' name='recBankId' value='<%=recBankId%>'>
		<input type='hidden' name='trandType' value='<%=trandType%>'>
		<input type='hidden' name='type' value='<%=typeMoney%>'>
		<input type='hidden' name='amount' value='<%=amount%>'>
		<input type='hidden' name='postscript' value='<%=postscript%>'>
		<input type='hidden' name='recFirmId' value='<%=recFirmId%>'>
		<fieldset width="95%">
			<legend>资金划转确认</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="50%" height="35">
				<tr height="35">
					<td align="right" width="20%">付款账户：&nbsp;</td>
					<td align="left" width="12%">	
					<%=payAccount%>
					</td>
					<td align="right" width="20%">收款账户：&nbsp;</td>
					<td align="left">	
					<%=recAccount%>
					</td>
					<td ></td><td ></td>
				</tr>
				<tr height="35">
					<td align="right">
					付款账户名：&nbsp;
					</td>
					<td align="left">	
					<%=payAccountName%>
					</td>
					<td align="right">		
					收款账户名：&nbsp;
					</td>
					<td align="left">	
					<%=recAccountName%>
					</td>
					<td ></td><td ></td>
				</tr>
				<tr height="35">
					<td align="right">
					币种：&nbsp;
					</td>
					<td align="left" colspan="3">
						人民币
					</td>
					<td ></td><td ></td>
				</tr>
				

				<tr height="35">
					<td align="right">交易金额：&nbsp;</td>
					<td align="left">			
					<%=amount%>
					</td>
					<td align="right">		
					大写金额：&nbsp;
					</td>
					<td align="left">	
					<%=amount%>
					</td>
					<td ></td><td ></td>
				</tr>
				
				<tr height="35">
					<td align="right">附言：&nbsp;</td>
					<td align="left" colspan="3">
						<%=postscript%>
					</td>
					<td ></td><td ></td>
				</tr>

				<tr height="35">					
					<td align="center" colspan=6>
						<button type="button" class="smlbtn" onclick="goback()">返回</button>&nbsp;
						<button type="button" class="smlbtn" onclick="dosubmit()">确认</button>&nbsp;						
					</td>
				</tr>


			</table>
		</fieldset>	  
	</form>
  </body>
</html>

