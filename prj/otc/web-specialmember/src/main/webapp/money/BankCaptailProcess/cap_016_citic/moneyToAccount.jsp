<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
String CITICBankID = "016";

CITICBankDAO dao = BankDAOFactory.getCITICDAO();

String firmID = (String)session.getAttribute("REGISTERID");
String filter=" and firmid='"+firmID+"' and bankid='"+CITICBankID+"' " ;
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
    <title>提现</title>
  </head>
<body style="overflow-y: hidden" onload="isCorrline();">
  <object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==" type="application/x-oleobject">
    </embed> 
</object>
  	<form id="frm" action="moneyToAccount2.jsp" method="post" name="frm">
	<input type="hidden" name="inoutMoney" value="3">
	<fieldset width="95%">
	<legend>中信银行附属账户提现到实体银行账户</legend>
	
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35">
					<td align="right" >会员编号：&nbsp;</td>
					<td align="left" >
						<input type="text" name="firmID" value="<%=firmID%>" id="firmID"  disabled='disabled' readonly class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="contactFalg">
					<td align="right"><%=CONTACTTITLE%>：&nbsp;</td>
					<td align="left">
						<input name="contact" id="contact"  readonly value="<%=Tool.delNull(corr.contact)%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="account1Flag">
					<td align="right">付款账号：&nbsp;</td>
					<td align="left">
						<input type="text" name="account1" id="account1"  readonly value="<%=Tool.delNull(corr.account1)%>"  class="input_text"  maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">是否跨行：&nbsp;</td>
					<td align="left">
					本行<input type="radio" name="InOutStart" value="0" onClick="isCorrline();" <%=!("1".equals(corr.isCrossline))?"checked":""%>>
					跨行<input type="radio" name="InOutStart" value="1" onClick="isCorrline();" <%="1".equals(corr.isCrossline)?"checked":""%>>
					</td>
				</tr>
				<tr height="35" id="accountFlag" >
					<td align="right">收款实体账户：&nbsp;</td>
					<td align="left">
						<input type="text" name="account" id="account" class="input_text" value="<%=Tool.delNull(corr.account)%>" maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="accountNameFlag">
					<td align="right">收款账户名：&nbsp;</td>
					<td align="left">
						<input type="text" name="accountName" id="accountName" value="<%=Tool.delNull(corr.accountName)%>" class="input_text"  maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="bankNameFlag"  style="display:none;">
					<td align="right">收款银行：&nbsp;</td>
					<td align="left">
						<input type="text" name="bankName" id="bankName" value="<%=Tool.delNull(corr.bankName)%>" class="input_text"  maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="OpenBankCodeFlag" style="display:none;">
					<td align="right">收款行号：&nbsp;</td>
					<td align="left">
						<input type="text" name="OpenBankCode" id="OpenBankCode" value="<%=Tool.delNull(corr.OpenBankCode)%>" class="input_text"  maxlength="30" style="width: 140px">
					</td>
				</tr>
				
				<tr height="35" id="moneyFlag">
					<td align="right">提现金额：&nbsp;</td>
					<td align="left">
						<input type="text" name="money" id="money"  class="input_text"  maxlength="30" style="width: 140px" onblur="checkNum()">
					</td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
			
			
		
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="15">
			<tr height="15">					
				<td align="center" colspan=2>
					<input type="button" id="sub_btn" class="smlbtn" onclick="doSubmit();" value="确定">&nbsp;
					<input type="button" id="bak_btn" class="smlbtn" onclick="window.close();"value="关闭">&nbsp;
					<input type="hidden" id="submitFlag" name="submitFlag" value="">
				</td>
			</tr>
		</table>
		</fieldset>	  
	</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
/**验证金额正确性*/
function checkNum() {
	if(frm.money.value!='') {
	  var txt = frm.money.value;//被校验的值
	  txt = txt.replace(/,/gi,"");
		var pattern= /^\+?([1-9]{1}[0-9]*|0)(\.[0-9]{1,2})?$/;
		if(!pattern.exec(txt)) {
			alert("请输入合法的人民币数值!");
			frm.money.value="";
			
		}else if(txt>=10000000000){
			alert("单笔出入金范围超出限制");
			frm.money.value="";
		}else{
			frm.money.value = txt;
		}
	}
}
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
	var InOutStart;
	var zt = document.getElementsByName("InOutStart"); 
	for(var i=0;i<zt.length;i++){
		if(zt[i].checked) {
			InOutStart=zt[i].value;
		}
	}
	if(InOutStart=='0'){
		document.getElementById("bankNameFlag").style.display = "none";
		document.getElementById("OpenBankCodeFlag").style.display = "none";
	}else{
		document.getElementById("bankNameFlag").style.display = "";
		document.getElementById("OpenBankCodeFlag").style.display = "";
	}
}

function doSubmit(){
	
	if(trim(frm.account.value) == "")
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
	else if(trim(frm.money.value)=="")
	{
		alert("请输入交易金额！");
		frm.money.focus();
	}
	else if(trim(frm.money.value)=="0")
	{
		alert("请输入正确的交易金额！");
		frm.money.focus();
	}
	else
	{
		document.getElementById("sub_btn").disabled = true;
		document.getElementById("bak_btn").disabled = true;
		alert("交易正在发往银行，请稍后...");
		frm.submit();
	}
}
</SCRIPT>