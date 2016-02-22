<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
	new ErrorCode().load();
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String contact = request.getParameter("contact");
	Vector<CorrespondValue> cps = dao.getCorrespondList(" and firmid='"+firmID+"' and bankID='005'");
	CorrespondValue corr = null;
	if(cps != null && cps.size()>0){
		corr = cps.get(0);
	}else{
		FirmValue firm = dao.getFirm(firmID);
		if(firm == null){
			out.print("δ��Ѱ���ͻ���Ϣ");
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
		/*�ദ����·��
		CapitalProcessorRMI cp = null;
		try{
			cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
		}catch(Exception e){
			e.printStackTrace();
		}*/
		long bak = -1;
		String str="�����ʽ��˺Ź���-ǩԼũ���˺�["+firmID+"]";
		String strs = "";
		if(corr == null){
			strs = "-δ��Ѱ���˺���Ϣ";
		}else{
			//corr.bankID = request.getParameter("bankID");
			corr.bankID = "005";
			corr.account = request.getParameter("account");
			corr.accountName = request.getParameter("accountName");
			corr.bankCardPassword = request.getParameter("bankCardPassword");
			if(corr.bankID == null || corr.bankID.trim().length()<=0){
				strs += "���в���Ϊ��";
			}else if(!openAccountBank(corr.bankID)){
				strs += corr.bankID+"�����в����г������ǩԼ";
			}else if(corr.isOpen==1){
				strs += "���˻���ũ���Ѿ���ǩԼ״̬�������ظ�ǩԼ";
			}else{
				try {
					//�ദ����·��
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
								strs = "������ "+ result.result;
							}
						}
					}else{
						strs= "�����˺�["+corr.contact+"]������["+corr.bankID+"]�ɹ�";
						bak = 0;
					}
				} catch(Exception e) {
					strs = "�����˺�["+corr.contact+"]������["+corr.bankID+"]ǩԼʱ����ϵͳ�쳣";
					e.printStackTrace();
				}
				LogValue lv = new LogValue();
				lv.setLogtype("2110");
				lv.setLogoprtype("E");
				lv.setResult(bak==0 ? 0 : 1);
				lv.setLogopr(AclCtrl.getLogonID(request));
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
	<link type="text/css" rel="stylesheet" href="../../lib/jquery/style/validator.css"></link>
    <title>(ũ��)����������ǩԼ</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
		<input type="hidden" name="submitFlag" id="submitFlag">
	<div style="overflow:auto;height:210;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;(ũ��)����������ǩԼ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="80">
						<input  name="firmID" disabled='disabled' value="<%=firmID%>" readonly type="hidden"  class="input_text" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>��&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=contact%>" type=text  class="input_text" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account');" onfocus="myfocus(account)" id="account" name="account" value="<%=Tool.delNull(corr.account)%>" type=text class="text" maxlength="30" style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="accountTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right"><%=ACCOUNTNAMETITLE%>��&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');" onfocus="myfocus(accountName)" id="accountName" name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text class="text" maxlength="30" style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="accountNameTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">�����˺����룺&nbsp;</td>
					<td align="left">
						<input onblur="myblur('bankCardPassword');" onfocus="myfocus(bankCardPassword)" id="bankCardPassword" name="bankCardPassword" type=password maxlength="6" class="text" maxlength="128" style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="bankCardPasswordTip" class=""></div></td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="sub_btn" class="btn_sec" onclick="doAdd();">ǩԼ</button>&nbsp;
					<button id="bak_btn" class="btn_cz" onclick="window.close();">����</button>&nbsp;
				</td>
			</tr>
		</table>
		<input type="hidden" name="bankID" id="bankID" value="005">
		<input type="hidden" name="005" id="005" value="ũҵ����">
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
var ErrorClass = "onError";
var CorrectClass = "";//"onCorrect";
var FocusClass = "";
var FocusMsg = "";
var ErrorMsg = "��֤����";
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
	//if("bankID"==userID){
		//flag = bankID(userID);
	//}else 
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
		tip.innerHTML="��������λ����";
	}else if(!number(user.value)){
		tip.innerHTML="��������λ����";
	}else if(user.value.length != 6){
		tip.innerHTML="��������λ����";
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
		tip.innerHTML="����Ϊ��";
	}else if(!is_Str(user.value,true,null)){
		tip.innerHTML="ֻ���������֡���ĸ����";
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
		tip.innerHTML="����Ϊ��";
	}else if(!number(user.value)){
		tip.innerHTML="ֻ����������";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}
function bankID(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="����ѡ��";
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
			if(confirm("ũ���Ѿ�ǩԼ��ȷ������ǩԼ?")){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
				idobj("submitFlag").value="do";
				frm.submit();
			}
		}else{
			var msg = "���Ƿ�ҪǩԼ�˻���";
			msg += "\n<%=CONTACTTITLE%>["+idobj('contact').value+"]";
			msg += "\n�����˺�["+idobj('account').value+"]";
			msg += "\n����["+idobj(idobj('bankID').value).value+"]";
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