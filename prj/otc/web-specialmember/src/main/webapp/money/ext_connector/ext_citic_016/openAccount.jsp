<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
	String CITICBankID = "016";
	String firmID = request.getParameter("firmID");
	
	CITICBankDAO dao = BankDAOFactory.getCITICDAO();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "'  ");
	CorrespondValue corr = new CorrespondValue();
	if(vc != null && vc.size() > 0){
		corr = vc.get(0);
	}
%>
<html >
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/report.css" type="text/css"/>
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>ǩԼ�����������ʺ�</title>
  </head>
  
  <body>
  
  	<form id="frm" action="openAccount2.jsp" method="post">
	<input type="hidden" name="firmID" value="<%=firmID%>">
		<fieldset width="95%">
			<legend>�󶨽������˻�����������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input name="contact" value="<%=corr.contact%>"  readonly type=text  class="input_text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ���У�&nbsp;</td>
					<td align="left">
						<input name="bankID" value="��������" readonly disabled='disabled' type=text  class="input_text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account1"  type=text class="input_text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����˻�����&nbsp;</td>
					<td align="left"> 
						<input name="accountName1" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="input_text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ʵ���ʺţ�&nbsp;</td>
					<td align="left">
						<input name="account"   type=text class="input_text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ʵ���˻�����&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="input_text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">�Ƿ���У�&nbsp;</td>
					<td align="left">
					����<input type="radio" name="InOutStart" value="0" onClick="isCorrline();" checked>
					����<input type="radio" name="InOutStart" value="1" onClick="isCorrline();" >
					</td>
				</tr>
				<tr height="35" id="bankNameFlag"  style="display:none;">
					<td align="right">�������ƣ�&nbsp;</td>
					<td align="left">
						<input type="text" name="bankName" id="bankName"  class="input_text"  maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="OpenBankCodeFlag" style="display:none;">
					<td align="right">֧���кţ�&nbsp;</td>
					<td align="left">
						<input type="text" name="OpenBankCode" id="OpenBankCode"  class="input_text"  maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select name="cardType">
							<option value="">��ѡ��</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>���֤</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>��֯��������</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>Ӫҵִ��</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>���˴���֤</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=Tool.delNull(corr.card)%>"  type=text  class="input_text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">ǩԼ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">ȡ��</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function getRadioValue(){
	var InOutStart;
	var zt = document.getElementsByName("InOutStart"); 
	for(var i=0;i<zt.length;i++){
		if(zt[i].checked) {
			InOutStart=zt[i].value;
		}
	}
	return InOutStart;
}
function isCorrline(){
	var InOutStart=getRadioValue();
	if(InOutStart=='0'){
		document.getElementById("bankNameFlag").style.display = "none";
		document.getElementById("OpenBankCodeFlag").style.display = "none";
	}else{
		document.getElementById("bankNameFlag").style.display = "";
		document.getElementById("OpenBankCodeFlag").style.display = "";
	}
}
function doAdd()
{	
	if(trim(frm.account1.value) == "")
	{
		alert("�����븽���˻�");
		frm.account1.focus();
	}
	else if(trim(frm.accountName1.value) == "")
	{
		alert("�����븽���˻���");
		frm.accountName1.focus();
	}
	else if(trim(frm.account.value) == "")
	{
		alert("������ʵ���˻�");
		frm.account.focus();
	}
	else  if(trim(frm.accountName.value) == "")
	{
		alert("�������˻���");
		frm.accountName.focus();
	}
	else if(trim(getRadioValue())=="1" &&trim(frm.bankName.value)=="")
	{
		alert("�Ǳ���ʵ���˻�������֧��������Ϣ��");
		frm.bankName.focus();
	}
	else if(trim(getRadioValue())=="1" &&trim(frm.OpenBankCode.value)=="")
	{
		alert("�Ǳ���ʵ���˻�������֧���кţ�");
		frm.OpenBankCode.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("��ѡ��֤������");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("������֤������");
		frm.card.focus();
	}
	else
	{
		
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>