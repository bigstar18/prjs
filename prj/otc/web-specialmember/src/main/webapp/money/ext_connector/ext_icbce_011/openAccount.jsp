<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
	String ICBCEBankID = "011";
	String firmID = request.getParameter("firmID");
	String account ="";
	ICBCEBankDAO dao = BankDAOFactory.getICBCEDAO();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmID = '" + firmID + "'  ");
	CorrespondValue corr = new CorrespondValue();
	if(vc != null && vc.size() > 0){
		corr = vc.get(0);
	}
	Vector<CorrespondValue> vc1 = dao.getCorrespondList(" and firmID = '" + firmID + "' and bankid='011' ");
	if(vc1!= null && vc1.size() > 0){
		account=vc1.get(0).account;
	}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>签约交易商银行帐号</title>
  </head>
  
  <body onload="changeCardType()">
  	<form id="frm" action="openAccount2.jsp" method="post">
	<input type="hidden" name="firmID" value="<%=firmID%>">
		<fieldset width="95%">
			<legend>绑定交易商账户和工商银行</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易账号：&nbsp;</td>
					<td align="left">
						<input name="contact" value="<%=corr.contact%>"  readonly type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">签约银行：&nbsp;</td>
					<td align="left">
						<input name="bankID" value="工商银行" readonly disabled='disabled' type=text  class="text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">银行帐号：&nbsp;</td>
					<td align="left">
						<input name="account"  value="<%=account%>" type=text class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">账户名：&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType" onchange="changeCardType();">
							<option value="">请选择</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>身份证</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>组织机构代码</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>营业执照</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>法人代码证</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input name="card" value="<%=Tool.delNull(corr.card)%>"  type=text  class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="account1_Tr" style="display:none;" >
					<td align="right">缴费编号：&nbsp;</td>
					<td align="left">
						<input name="account1"  type=text class="text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doAdd();">签约</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">取消</button>&nbsp;
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
var b2cflag=false;
function changeCardType(){
	if(b2cflag||trim(frm.cardType.value)!="1"){
	
		document.getElementById("account1_Tr").style.display = "none";
	}else{
		document.getElementById("account1_Tr").style.display = "";
	}
}
function doAdd()
{	
	if(trim(frm.account.value) == "")
	{
		alert("请输入银行账户");
		frm.account.focus();
	}
	else  if(trim(frm.accountName.value) == "")
	{
		alert("请输入账户名");
		frm.accountName.focus();
	}
	else if(trim(frm.cardType.value) == "")
	{
		alert("请选择证件类型");
		frm.cardType.focus();
	}
	else if(trim(frm.card.value) == "")
	{
		alert("请输入证件号码");
		frm.card.focus();
	}
	else if((!b2cflag)&&trim(frm.cardType.value)=="1"&&trim(frm.account1.value) == "")
	{
		alert("请输入缴费编号");
		frm.account1.focus();
	}
	else 
	{
		
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>