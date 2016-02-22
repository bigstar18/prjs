<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	FirmMessageVo memberstatus = dao.getFirmMSG((String)session.getAttribute("REGISTERID"));
	boolean canOpenCustomer = false;
	if(memberstatus != null && "N".equalsIgnoreCase(memberstatus.getStatus())){
		canOpenCustomer = true;
	}
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"' and bankID='005'");
	CorrespondValue corr = null;
	if(cps != null && cps.size()>0){
		corr = cps.get(0);
	}else{
		FirmValue firm = dao.getFirm(firmID);
		if(firm == null){
			out.print("未查寻到客户信息");
			return;
		}else{
			corr = new CorrespondValue();
			corr.firmID = firm.firmID;
			corr.contact = firm.contact;
			corr.card = firm.card;
			corr.cardType = firm.cardType;
			corr.accountName = firm.firmName;
		}
	}
	if("do".equals(request.getParameter("submitFlag"))){
		long bak = -1;
		String str=CONTACTTITLE+"管理-签约农行账号["+firmID+"]";
		String strs = "";
		if(!canOpenCustomer){
			strs = "您的账号暂时冻结，不能未客户开户";
		}else if(corr == null){
			strs = "未查寻到账号信息";
		}else if(corr.isOpen==1){
			strs += "该账户在农行已经是签约状态，不能重复签约";
		}else{
			corr.bankID = request.getParameter("bankID");
			corr.account = request.getParameter("account");
			corr.accountName = request.getParameter("accountName");
			corr.bankCardPassword = request.getParameter("bankCardPassword");
			if(corr.bankID == null || corr.bankID.trim().length()<=0){
				strs = "银行不能为空";
			}else if(!openAccountBank(corr.bankID)){
				strs = corr.bankID+"银行不能市场端签约";
			}else{
				try {
					CapitalProcessorRMI cp = null;
					try{
						cp = getBankUrl(corr.bankID);
					}catch(Exception e){
						e.printStackTrace();
					}
					ReturnValue result=cp.openAccountMarket(corr);
					if(result.result<0){
						strs = result.remark;
						if(strs == null || strs.trim().length()<=0){
							if(ErrorCode.error.get(result.result) != null){
								strs = ErrorCode.error.get(result.result);
							}else{
								strs = "返回码["+ result.result+"]";
							}
						}
					}else{
						strs = FIRMTITLE+"["+corr.firmID+"]签约成功";
						bak = 0;
					}
				} catch(Exception e) {
					strs = FIRMTITLE+"["+corr.firmID+"]签约时返回系统异常";
					e.printStackTrace();
				}
				LogValue lv = new LogValue();
				lv.setLogtype("2210");
				lv.setLogoprtype("M");
				lv.setResult(bak==0 ? 0 : 1);
				lv.setLogopr((String)session.getAttribute("CURRENUSERID"));
				lv.setMark((String)session.getAttribute("REGISTERID"));
				lv.setIp(computerIP);
				lv.setLogcontent(str+"-"+strs);
				dao.log(lv);
			}
		}
	%>
		<script>
			alert('<%=strs%>');
			<%
				if(bak == 0){
			%>
				window.returnValue="1";
				window.close();
			<%
				}
			%>
		</script>
	<%
	}
%>
<html>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>(农行)交易所发起签约</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag" id="submitFlag">
	<div style="overflow:auto;height:260;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;(农行)交易所发起签约</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35" style="display: none;">
					<td align="right" width="30%"><%=FIRMTITLE%>：&nbsp;</td>
					<td align="left" width="20%">
						<input name="firmID" disabled='disabled' value="<%=firmID%>" readonly type=text  class="input_text" style="width: 140px">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right" width="30%"><%=CONTACTTITLE%>：&nbsp;</td>
					<td align="left" width="20%">
						<input name="contact" disabled='disabled' readonly value="<%=corr.contact%>" type=text  class="input_text" style="width: 140px">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right"><%=ACCOUNTTITLE%>：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account');" onfocus="myfocus(account)" id="account" name="account" value="<%=Tool.delNull(corr.account)%>" type=text class="text" maxlength="30" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="accountTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right"><%=ACCOUNTNAMETITLE%>：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');" onfocus="myfocus(accountName)" id="accountName" name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text class="text" maxlength="30" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="accountNameTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">银行卡密码：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('bankCardPassword');" onfocus="myfocus(bankCardPassword)" id="bankCardPassword" name="bankCardPassword" type=password maxlength="6" class="text" maxlength="128" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="bankCardPasswordTip" class=""></div></td>
				</tr>
				<%
					if(!canOpenCustomer){
				%>
				<tr height="35">
					<td align="center" colspan="3"><font color="#FF0000">您的账户暂时被冻结，不能为客户签约</font></td>
				</tr>
				<%
					}
				%>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="sub_btn" class="btn_sec" <%=canOpenCustomer ? "" : "disabled='disabled'"%> onclick="doAdd();">签约</button>&nbsp;
					<button id="bak_btn" class="btn_cz" onclick="window.close();">返回</button>&nbsp;
				</td>
			</tr>
		</table>
		<input type="hidden" name="bankID" id="bankID" value="005">
		<input type="hidden" name="005" id="005" value="农业银行">
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
var ErrorClass = "onError";
var CorrectClass = "";//"onCorrect";
var FocusClass = "";
var FocusMsg = "";
var ErrorMsg = "验证错误";
var SuccessMsg = "";
function idobj(userID){
	return document.getElementById(userID);
}
function tipclass(userID,flag){
	var tip = idobj(userID+"Tip");
	if(flag){
		tip.className=CorrectClass;
	}else{
		tip.className=ErrorClass;
	}
}
function myblur(userID){
	var flag = true;
	if("account"==userID){
		flag = account(userID);
	}else if("accountName"==userID){
		flag = accountName(userID);
	}else if("bankCardPassword"==userID){
		flag = bankCardPassword(userID);
	}else{
		if(!account("account")) flag = false;
		if(!accountName("accountName")) flag = false;
		if(!bankCardPassword("bankCardPassword")) flag = false;
	}
	return flag;
}
function bankCardPassword(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML="请输入六位数字";
	}else if(!number(user.value)){
		tip.innerHTML="请输入六位数字";
	}else if(user.value.length != 6){
		tip.innerHTML="请输入六位数字";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}
function accountName(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="不能为空";
	}else if(!is_Str(user.value,true,null)){
		tip.innerHTML="只能输入数字、字母或汉字";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}
function account(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="不能为空";
	}else if(!number(user.value)){
		tip.innerHTML="只能输入数字";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}
function myfocus(){
}
function doAdd(){
	var flag = true;
	flag = myblur("all");
	if(flag){
		if('<%=corr.isOpen%>'=='1'){
			if(confirm("农行已经签约，确认重新签约?")){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
				idobj("submitFlag").value="do";
				frm.submit();
			}
		}else{
			var msg = "您确定要签约账户：";
				msg += "\n客户:["+idobj('firmID').value+"]";
				msg += "\n账号:["+idobj('account').value+"]";
				msg += "\n银行:["+idobj(idobj('bankID').value).value+"]";
			if(confirm(msg)){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
				idobj("submitFlag").value="do";
				frm.submit();
			}
		}
	}
}
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function isEmpty(s){
	if(s.trim().length<=0){
		return true;
	}
	return false;
}
function number(s){
	if(isEmpty(s)){
		return true;
	}
	var patrn=/^\d*$/;
	if (patrn.exec(s)) {
		return true ;
	}
	return false ;
}
function is_Str(s,ch,vec){
	if(isEmpty(s)){
		return true;
	}
	var china = "";
	var strs = "";
	if(ch){
		china = "\\u4e00-\\u9fa5";
	}
	if(vec != null){
		for(var i=0;i<vec.length;i++){
			strs += "|\\"+vec[i];
		}
	}
	var matchs='\^[0-9A-Za-z'+china+strs+']{1,}\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
//-->
</SCRIPT>