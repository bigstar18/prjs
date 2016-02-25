<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%

	BankDAO dao = BankDAOFactory.getDAO();

	String id = request.getParameter("id");	
	Vector<BankTransferValue> bankTransferList = dao.getBankTransferList(" and id = " + id);
	BankTransferValue bankTransferValue = bankTransferList.get(0);

	int flag = -1;
	if(request.getParameter("flag")!=null&&!request.getParameter("flag").equals("")){
		flag = Tool.strToInt(request.getParameter("flag"));
	}

	String payAccount = null;
	String recAccount = null;
	String payAccountName = null;
	String recAccountName = null;

	
	Vector<DicValue> list = dao.getDicList(" where type = 2");


	for (int i = 0; i < list.size(); i++) {
		DicValue val = (DicValue) list.get(i);
		//System.out.println("val.bankID=="+val.bankID);
		//System.out.println("payBankId=="+payBankId);
		if (val.bankID.equals(bankTransferValue.payBankId)&&val.name.equals("marketAccount")) {
			payAccount = val.value;
		} 
		if (val.bankID.equals(bankTransferValue.recBankId)&&val.name.equals("marketAccount")) {
			recAccount = val.value;
		} 
		if (val.bankID.equals(bankTransferValue.payBankId)&&val.name.equals("accountName")) {
			payAccountName = val.value;
		} 
		if (val.bankID.equals(bankTransferValue.recBankId)&&val.name.equals("accountName")) {
			recAccountName = val.value;
		}
	}
	

%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�ʽ�ת</title>
  </head>
  
    <SCRIPT LANGUAGE="JavaScript">
	<!--
	function pass(){
		frm.rst.value = '0';
		frm.submit();
	}
	function refuse(){
		frm.rst.value = '1';
		frm.submit();
	}
	function back(){
		window.location = "transferList.jsp";
	}
	//-->
	</SCRIPT>

  <body>
  	<form id="frm" name="frm" action="auditDo.jsp" method="post">
		<input type='hidden' name='id' value='<%=id%>'>	
		<input type='hidden' name='rst' value='0'>	
		<input type='hidden' name='type' value='<%=bankTransferValue.type%>'>	
		<fieldset width="95%">
			<legend>
			<%if(flag==0){%>�ʽ�ת�鿴<%}else{%>�ʽ�ת���<%}%>
			</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right" width="12%">�����˻���&nbsp;</td>
					<td align="left" width="12%">	
					<%=payAccount%>
					</td>
					<td align="right" width="12%">	�տ��˻���&nbsp;					
					</td>
					<td align="left"  width="12%">	
					<%=recAccount%>
					</td>
					<td  width="12%"></td><td ></td>
				</tr>
				<tr height="35">
					<td align="right">
					�����˻�����&nbsp;
					</td>
					<td align="left">	
					<%=payAccountName%>
					</td>
					<td align="right">		
					�տ��˻�����
					</td>
					<td align="left">	
					<%=recAccountName%>
					</td>
					<td ></td><td ></td>
				</tr>


				<tr height="35">
					<td align="right">
					���֣�&nbsp;
					</td>
					<td align="left" colspan="3">
						�����
					</td>
					<td ></td><td ></td>
				</tr>
				

				<tr height="35">
					<td align="right">���׽�&nbsp;</td>
					<td align="left">			
					<%=bankTransferValue.money%>
					</td>
					<td align="right">		
					��д��
					</td>
					<td align="left">	
					<%=String.valueOf(bankTransferValue.money)%>
					</td>
					<td ></td><td ></td>
				</tr>
				
				<tr height="35">
					<td align="right">���ԣ�&nbsp;</td>
					<td align="left" colspan="3">
						<%=bankTransferValue.note%>
					</td>
					<td ></td><td ></td>
				</tr>

				<tr height="35">					
					<td align="center" colspan=6>
					<%if(flag==0){%>
						<button type="button" class="smlbtn" onclick="back()">����</button>&nbsp;		
					<%}else{%>
						<button type="button" class="smlbtn" onclick="refuse()">�ܾ�</button>&nbsp;
						<button type="button" class="smlbtn" onclick="pass()">ͨ��</button>&nbsp;		
					<%}%>
					</td>
				</tr>
			</table>
		</fieldset>	  
	</form>
  </body>
</html>

