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
    <title>签约交易商银行帐号</title>
  </head>
  
  <body>
  
  	<form id="frm" action="openAccount2.jsp" method="post">
	<input type="hidden" name="firmID" value="<%=firmID%>">
		<fieldset width="95%">
			<legend>绑定交易商账户和中信银行</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">交易账号：&nbsp;</td>
					<td align="left">
						<input name="contact" value="<%=corr.contact%>"  readonly type=text  class="input_text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">签约银行：&nbsp;</td>
					<td align="left">
						<input name="bankID" value="中信银行" readonly disabled='disabled' type=text  class="input_text" maxlength="10"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">附属帐号：&nbsp;</td>
					<td align="left">
						<input name="account1"  type=text class="input_text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">附属账户名：&nbsp;</td>
					<td align="left"> 
						<input name="accountName1" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="input_text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">实体帐号：&nbsp;</td>
					<td align="left">
						<input name="account"   type=text class="input_text" maxlength="30"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">实体账户名：&nbsp;</td>
					<td align="left"> 
						<input name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="input_text" maxlength="64"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">是否跨行：&nbsp;</td>
					<td align="left">
					本行<input type="radio" name="InOutStart" value="0" onClick="isCorrline();" checked>
					跨行<input type="radio" name="InOutStart" value="1" onClick="isCorrline();" >
					</td>
				</tr>
				<tr height="35" id="bankNameFlag"  style="display:none;">
					<td align="right">银行名称：&nbsp;</td>
					<td align="left">
						<input type="text" name="bankName" id="bankName"  class="input_text"  maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35" id="OpenBankCodeFlag" style="display:none;">
					<td align="right">支付行号：&nbsp;</td>
					<td align="left">
						<input type="text" name="OpenBankCode" id="OpenBankCode"  class="input_text"  maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select name="cardType">
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
						<input name="card" value="<%=Tool.delNull(corr.card)%>"  type=text  class="input_text" maxlength="30"><span class=star>*</span>
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
		alert("请输入附属账户");
		frm.account1.focus();
	}
	else if(trim(frm.accountName1.value) == "")
	{
		alert("请输入附属账户名");
		frm.accountName1.focus();
	}
	else if(trim(frm.account.value) == "")
	{
		alert("请输入实体账户");
		frm.account.focus();
	}
	else  if(trim(frm.accountName.value) == "")
	{
		alert("请输入账户名");
		frm.accountName.focus();
	}
	else if(trim(getRadioValue())=="1" &&trim(frm.bankName.value)=="")
	{
		alert("非本行实体账户请输入支行名称信息！");
		frm.bankName.focus();
	}
	else if(trim(getRadioValue())=="1" &&trim(frm.OpenBankCode.value)=="")
	{
		alert("非本行实体账户请输入支行行号！");
		frm.OpenBankCode.focus();
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
	else
	{
		
		frm.submitFlag.value = "do";
		frm.submit();
	}
}

//-->
</SCRIPT>