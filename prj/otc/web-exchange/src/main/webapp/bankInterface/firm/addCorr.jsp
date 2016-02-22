<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	/*�ദ����·��
	CapitalProcessorRMI cp = null;
	try
	{
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}*/
	String firmID = request.getParameter("firmID");
	if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
	{
		String bankID = request.getParameter("bankID");
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		String account = request.getParameter("account");
		String contact = request.getParameter("contact");
		String accountName = Tool.delNull(request.getParameter("accountName"));
		String card = request.getParameter("card");
		String cardType = Tool.delNull(request.getParameter("cardType"));
		String account1 = Tool.delNull(request.getParameter("account1"));
		
		CorrespondValue corr = new CorrespondValue();
		corr.account = account;
		corr.contact = contact;
		corr.account1=account1;
		corr.accountName = accountName;
		corr.card = card;
		corr.bankID = bankID;
		corr.firmID = firmID;
		corr.status = 1;
		corr.cardType=cardType;
		corr.isOpen = 0;
		long result = 0;
		if(checkAccount(bankID)){
			corr.account = Tool.getConfig("DefaultAccount");
		}
		try
		{
			if(dao.getCorrespond(bankID,firmID,account)!=null)
			{
				result = -2;
			}
			else
			{
				//�ദ����·��
				CapitalProcessorRMI cp = null;
				try{
					cp = getBankUrl(bankID);
				}catch(Exception e){
					e.printStackTrace();
				}
				result = cp.rgstAccount(corr);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = -1;
		}
		String msg = "";
		if(result == 0)
		{
			msg = "�����˺������˺�ע��ɹ���";
		}
		else if(result == -2)
		{
			msg = "�����˺������˺Ŷ�Ӧ��ϵ�Ѵ��ڣ�";
		}
		else
		{
			msg = "�����˺������˺�ע��ʧ�ܣ�";
		}	

		%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//window.opener.location.reload();
				window.returnValue="1";
				window.close();
				//-->
				</SCRIPT>	
			<%
		lv.setLogcontent(msg+" ���д��룺"+bankID+"�����˺ţ�"+firmID);
		dao.log(lv);
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../lib/jquery/style/validator.css"></link>
    <title>ע�ύ���˺������˺�</title>
  </head>
  <body style="overflow-y:hidden">
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:400;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_tit��e"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;ע�ύ���˺������ʺ�</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left" colspan=2>
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="input_text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right" width="25%">���У�&nbsp;</td>
					<td align="left" width="30%">
						<%
						Vector bankList = dao.getBankList(" and validFlag=0 ");
						%>
						<select onblur="myblur('bankID');" onfocus="myfocus('bankID')" id="bankID" name="bankID" class="normal" style="width: 140px">
							<OPTION value="">��ѡ��</OPTION>
							<%
							for(int i=0;i<bankList.size();i++){
								BankValue bank = (BankValue)bankList.get(i);
								%>
								<option value="<%=bank.bankID%>"><%=bank.bankName%></option>	
								<%
							}
							%>
						</select><span class=star>*</span>
					</td>
					<td><div id="bankIDTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account');" onfocus="myfocus('account')" id="account" name="account" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="accountTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35">
					<td align="right">�ظ������˺ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('reaccount');" onfocus="myfocus('reaccount')" id="reaccount" name="reaccount" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="reaccountTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35">
					<td align="right">����ǩԼ�ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('contact');" onfocus="myfocus('contact')" id="contact" name="contact" value="" type=text  class="input_text" maxlength="15" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="contactTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35" style="display: none;">
					<td align="right">�����ڲ��˺ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account1');" onfocus="myfocus('account1')" id="account1" name="account1" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="account1Tip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35">
					<td align="right">�˻�����&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');" onfocus="myfocus('accountName')" id="accountName" name="accountName" value="" type=text  class="input_text" maxlength="128" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="accountNameTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select onblur="myblur('cardType');" onfocus="myfocus('cardType')" id="cardType" name="cardType">
							<option value="">��ѡ��</option>
							<option value="1">���֤</option>
							<!--<option value="2">����֤</option>
							<option value="3">���ڻ���</option>
							<option value="4">���ڱ�</option>
							<option value="5">ѧԱ֤</option>
							<option value="6">����֤</option>
							<option value="7">��ʱ���֤</option>-->
							<option value="8">��֯��������</option>
							<option value="9">Ӫҵִ��</option>
							<option value="a">���˴���֤</option>
						</select><span class=star>*</span>
					</td>
					<td><div id="cardTypeTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
				<tr height="35">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input id="card" onblur="myblur('card');" onfocus="myfocus('card')" name="card" value="" type=text  class="input_text" maxlength="18" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="cardTip" class="onFocus">��ʾ��Ϣ</div></td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="submitButton" class="btn_sec" onclick="doAdd();">���</button>&nbsp;
					<button class="btn_cz" onclick="window.close();">ȡ��</button>&nbsp;
					<input type=hidden name=submitFlag value="">
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
var ErrorClass = "onError";
var CorrectClass = "onFocus";//"onCorrect";
var FocusClass = "onFocus";
var FocusMsg = "��ʾ��Ϣ";
var ErrorMsg = "��֤����";
var SuccessMsg = "��ʾ��Ϣ";
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
	if("bankID"==userID){
		flag = bankID(userID);
	}else if("account"==userID){
		flag = account(userID);
	}else if("reaccount"==userID){
		flag = reaccount(userID);
	}else if("contact"==userID){
		flag = contact(userID);
	}else if("account1"==userID){
		flag = account1(userID);
	}else if("accountName"==userID){
		flag = accountName(userID);
	}else if("cardType"==userID){
		flag = cardType(userID);
	}else if("card"==userID){
		flag = card(userID);
	}else{
		if(!bankID("bankID")) flag = false;
		if(!account("account")) flag = false;
		if(!reaccount("reaccount")) flag = false;
		if(!contact("contact")) flag = false;
		//if(!account1("account1")) flag = false;
		if(!accountName("accountName")) flag = false;
		if(!cardType("cardType")) flag = false;
		if(!card("card")) flag = false;
	}
	return flag;
}
function card(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="����Ϊ��";
	}else{
		var type = idobj("cardType");
		if(type.value==1){
			if(is_CardID(user.value)){
				tip.innerHTML=SuccessMsg;
				flag = true;
			}else{
				tip.innerHTML="���֤���Ϸ�";
			}
		}else{
			var vec = new Array("-");
			if(!is_Str(user.value,false,vec)){
				tip.innerHTML="֤���Ų��Ϸ�";
			}else if(user.value.length<5 || user.value.length>20){
				tip.innerHTML="������ 5 �� 20 ֮��";
			}else{
				tip.innerHTML=SuccessMsg
				flag = true;
			}
		}
	}
	tipclass(userID,flag);
	return flag;
}
function cardType(userID){
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
function accountName(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="����Ϊ��";
	}else if(!is_Str(user.value,true,null)){
		tip.innerHTML="ֻ���������֡���ĸ�ͺ���";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}
function account1(userID){
	return account(userID);
}
function contact(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML="����Ϊ��";
	}else if(!is_Str(user.value,null,null)){
		tip.innerHTML="ֻ���������ֻ���ĸ";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}
function reaccount(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var account = idobj("account");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="����Ϊ��";
	}else if(!number(user.value)){
		tip.innerHTML="ֻ����������";
	}else if(user.value==account.value){
		flag = true;
		tip.innerHTML=SuccessMsg;
	}else{
		tip.innerHTML="���������˺Ų�һ��";
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
function myfocus(userID){
}
function doAdd() {
	var flag = false;
	if(myblur("all")){
		flag = true;
	}
	if(flag){
		if(confirm("��ȷ�������Ϣ��")){
			frm.submitFlag.value = "do";
			frm.submit();
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
var aCity={11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}; 

function is_CardID(sId){
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) {
		return false;
	}//"����������֤���Ȼ��ʽ����"; 
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) {
		return false;
	}//"������֤�����Ƿ�"; 
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
		return false;
	}//"���֤�ϵĳ������ڷǷ�"; 
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) {
		return false;
	}//"����������֤�ŷǷ�"; 
	return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"��":"Ů") 
}
//-->
</SCRIPT>