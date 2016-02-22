<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	/*多处理器路由
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
				//多处理器路由
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
			msg = "交易账号银行账号注册成功！";
		}
		else if(result == -2)
		{
			msg = "交易账号银行账号对应关系已存在！";
		}
		else
		{
			msg = "交易账号银行账号注册失败！";
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
		lv.setLogcontent(msg+" 银行代码："+bankID+"交易账号："+firmID);
		dao.log(lv);
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../lib/jquery/style/validator.css"></link>
    <title>注册交易账号银行账号</title>
  </head>
  <body style="overflow-y:hidden">
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:400;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_tit账e"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;注册交易账号银行帐号</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35">
					<td align="right">交易账号：&nbsp;</td>
					<td align="left" colspan=2>
						<input name="firmID" value="<%=firmID%>" readonly type=text  class="input_text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right" width="25%">银行：&nbsp;</td>
					<td align="left" width="30%">
						<%
						Vector bankList = dao.getBankList(" and validFlag=0 ");
						%>
						<select onblur="myblur('bankID');" onfocus="myfocus('bankID')" id="bankID" name="bankID" class="normal" style="width: 140px">
							<OPTION value="">请选择</OPTION>
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
					<td><div id="bankIDTip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35">
					<td align="right">银行账号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account');" onfocus="myfocus('account')" id="account" name="account" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="accountTip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35">
					<td align="right">重复银行账号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('reaccount');" onfocus="myfocus('reaccount')" id="reaccount" name="reaccount" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="reaccountTip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35">
					<td align="right">银行签约号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('contact');" onfocus="myfocus('contact')" id="contact" name="contact" value="" type=text  class="input_text" maxlength="15" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="contactTip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35" style="display: none;">
					<td align="right">银行内部账号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account1');" onfocus="myfocus('account1')" id="account1" name="account1" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="account1Tip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35">
					<td align="right">账户名：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');" onfocus="myfocus('accountName')" id="accountName" name="accountName" value="" type=text  class="input_text" maxlength="128" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="accountNameTip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select onblur="myblur('cardType');" onfocus="myfocus('cardType')" id="cardType" name="cardType">
							<option value="">请选择</option>
							<option value="1">身份证</option>
							<!--<option value="2">军官证</option>
							<option value="3">国内护照</option>
							<option value="4">户口本</option>
							<option value="5">学员证</option>
							<option value="6">退休证</option>
							<option value="7">临时身份证</option>-->
							<option value="8">组织机构代码</option>
							<option value="9">营业执照</option>
							<option value="a">法人代码证</option>
						</select><span class=star>*</span>
					</td>
					<td><div id="cardTypeTip" class="onFocus">提示信息</div></td>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input id="card" onblur="myblur('card');" onfocus="myfocus('card')" name="card" value="" type=text  class="input_text" maxlength="18" style="width: 140px"><span class=star>*</span>
					</td>
					<td><div id="cardTip" class="onFocus">提示信息</div></td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="submitButton" class="btn_sec" onclick="doAdd();">添加</button>&nbsp;
					<button class="btn_cz" onclick="window.close();">取消</button>&nbsp;
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
var FocusMsg = "提示信息";
var ErrorMsg = "验证错误";
var SuccessMsg = "提示信息";
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
		tip.innerHTML="不能为空";
	}else{
		var type = idobj("cardType");
		if(type.value==1){
			if(is_CardID(user.value)){
				tip.innerHTML=SuccessMsg;
				flag = true;
			}else{
				tip.innerHTML="身份证不合法";
			}
		}else{
			var vec = new Array("-");
			if(!is_Str(user.value,false,vec)){
				tip.innerHTML="证件号不合法";
			}else if(user.value.length<5 || user.value.length>20){
				tip.innerHTML="长度在 5 到 20 之间";
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
		tip.innerHTML="必须选择";
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
		tip.innerHTML="只能输入数字、字母和汉字";
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
		tip.innerHTML="不能为空";
	}else if(!is_Str(user.value,null,null)){
		tip.innerHTML="只能输入数字或字母";
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
		tip.innerHTML="不能为空";
	}else if(!number(user.value)){
		tip.innerHTML="只能输入数字";
	}else if(user.value==account.value){
		flag = true;
		tip.innerHTML=SuccessMsg;
	}else{
		tip.innerHTML="两次银行账号不一致";
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
function bankID(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="必须选择";
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
		if(confirm("您确定添加信息吗？")){
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
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}; 

function is_CardID(sId){
	var iSum=0 ;
	var info="" ;
	if(!/^\d{17}(\d|x)$/i.test(sId)) {
		return false;
	}//"你输入的身份证长度或格式错误"; 
	sId=sId.replace(/x$/i,"a"); 
	if(aCity[parseInt(sId.substr(0,2))]==null) {
		return false;
	}//"你的身份证地区非法"; 
	sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
	var d=new Date(sBirthday.replace(/-/g,"/")) ;
	if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
		return false;
	}//"身份证上的出生日期非法"; 
	for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
	if(iSum%11!=1) {
		return false;
	}//"你输入的身份证号非法"; 
	return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女") 
}
//-->
</SCRIPT>