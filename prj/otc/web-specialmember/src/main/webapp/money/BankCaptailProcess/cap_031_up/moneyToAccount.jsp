<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>


<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
String UPBankID = "031";
CapitalProcessorRMI cp = null;	
	try {		
		cp = getBankUrl(UPBankID);
	} catch(Exception e) {
		e.printStackTrace();
	}
long result = -1;
String results = result+"";
boolean falg =true ;
String pwd = request.getParameter("password");
result = cp.isPassword((String)session.getAttribute("REGISTERID"),pwd);
			if(result == 1){
				falg = false;
				results="������������";
			}else if(result == -1){
				falg = false;
				results="�ʽ�������֤ʧ��";
			}else if(result == -2){
				falg = false;
				results="δ�鵽������";
			}
			
if(!falg){
%>
<SCRIPT LANGUAGE="JavaScript">
alert('<%=results%>');
window.close();
</SCRIPT>

<%

}
UPBankDAO dao = BankDAOFactory.getUPDAO();
String inoutMoney = request.getParameter("inoutMoney");
String money = request.getParameter("money");
String fl = "  ";
if("0".equals(inoutMoney)){
fl=" where type=2";
}
String firmID = (String)session.getAttribute("REGISTERID");
String filter=" and firmid='"+firmID+"' and bankid='"+UPBankID+"' " ;
Vector<CorrespondValue> corVector = dao.getCorrespondList(filter);
CorrespondValue corr = new CorrespondValue();
if(corVector!=null&&corVector.size()>0){
	corr=corVector.get(0);
}
%>
<html xmlns:MEBS>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/report.css" type="text/css"/>
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>ת��</title>
  </head>
<body style="overflow-y: hidden">
  
  	<form id="frm" action="moneyToAccount2.jsp" method="post" name="frm">
	
	<fieldset width="95%">
	<legend>�������մ���</legend>
	
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35">
					<td align="right" >��Ա��ţ�&nbsp;</td>
					<td align="left" >
						<input type="text" name="firmID" value="<%=firmID%>" id="firmID"  disabled='disabled' readonly class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="contactFalg">
					<td align="right"><%=CONTACTTITLE%>��&nbsp;</td>
					<td align="left">
						<input name="contact" id="contact"  readonly value="<%=Tool.delNull(corr.contact)%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				
				<tr height="35">
					<td align="right">�����&nbsp;</td>
					<td align="left">
						<select  name="inoutMoney" class="normal" style="width: 80px" >
							<option value="0" checked="<%= "0".equals(inoutMoney)? "checked" : ""%>"> ���</option>
							<option value="1" checked="<%= "1".equals(inoutMoney)? "checked" : ""%>">����</option>
						</select>
				
					</td>
				</tr>
				<tr height="35">
				
					<td align="right">�����У�&nbsp;</td>
					<td align="left">
					<%
						Vector bankCode = dao.getBankCodeList(fl);
						%>
						<select name="OpenBankCode">
						<option value="">��ѡ��</option>
						<%
							for(int i=0;i<bankCode.size();i++)
							{
								BankCodes bank = (BankCodes)bankCode.get(i);
								%>
								<option value="<%=bank.bankCode%>"><%=bank.bankName%></option>	
								<%
							}
							%>
							
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">���������ƣ�&nbsp;</td>
					<td align="left">
						<input name="bankName" value="" type=text  class="text" maxlength="50" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">������ʡ�ݣ�&nbsp;</td>
					<td align="left">
						<input name="bankProvince" value="" type=text  class="text" maxlength="20" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����������У�&nbsp;</td>
					<td align="left">
						<input name="bankCity" value="" type=text  class="text" maxlength="20" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�˻����ͣ�&nbsp;</td>
					<td align="left">
						<select name="accountType">
							<option value="00">���п�</option>
							<option value="01">����</option>
							<option value="02">���ÿ�</option>
						</select><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="account" >
					<td align="right">�˻���&nbsp;</td>
					<td align="left">
						<input type="text" name="account" id="account" class="input_text" value="<%=Tool.delNull(corr.account)%>"  style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="accountName">
					<td align="right">�˻�����&nbsp;</td>
					<td align="left">
						<input type="text" name="accountName" id="accountName" value="<%=Tool.delNull(corr.accountName)%>" class="input_text"  style="width: 140px">
					</td>
				</tr>
				
				
				<tr height="35" id="money">
					<td align="right">��&nbsp;</td>
					<td align="left">
						<input type="text" name="money" id="money"  value="<%=money%>"  style="width: 140px" >
					</td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
			
			
		
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="15">
			<tr height="15">					
				<td align="center" colspan=2>
				<input id="sub_btn" type="button" class="smlbtn" value="ȷ��" onclick="doSubmit();">
				<input id="bak_btn" type="button" class="smlbtn" value="�ر�" onclick="window.close();">
					
					<input type="hidden" id="submitFlag" name="submitFlag" value="">
				</td>
			</tr>
		</table>
		</fieldset>	  
	</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
/**��֤�����ȷ��*/
function checkNum() {
	if(frm.money.value!='') {
	  var txt = frm.money.value;//��У���ֵ
	  txt = txt.replace(/,/gi,"");
		var pattern= /^\+?([1-9]{1}[0-9]*|0)(\.[0-9]{1,2})?$/;
		if(!pattern.exec(txt)) {
			alert("������Ϸ����������ֵ!");
			frm.money.value="";
			
		}else if(txt>=10000000000){
			alert("���ʳ����Χ��������");
			frm.money.value="";
		}else{
			frm.money.value = txt;
		}
	}
}



function doSubmit(){
	
	if(trim(frm.account.value) == "")
	{
		alert("������ʵ���˻�");
		frm.account.focus();
	}
	else  if(trim(frm.accountName.value) == "")
	{
		alert("�������˻���");
		frm.accountName.focus();
	}
	
	else if(trim(frm.OpenBankCode.value)=="")
	{
		alert("������֧���кţ�");
		frm.OpenBankCode.focus();
	}
	else if(trim(frm.money.value)=="")
	{
		alert("�����뽻�׽�");
		frm.money.focus();
	}
	else if(trim(frm.money.value)=="0")
	{
		alert("��������ȷ�Ľ��׽�");
		frm.money.focus();
	}
	else
	{
		
		document.getElementById("sub_btn").disabled = true;
		frm.submit();
	}
}
</SCRIPT>