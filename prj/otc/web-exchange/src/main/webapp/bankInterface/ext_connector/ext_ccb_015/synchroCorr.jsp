<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>

<base target="_self"> 
<%
	String CCBbankID="015";
	CCBCapitalProcessorRMI cp=getCCBBankUrl(CCBbankID);
	String firmID=request.getParameter("firmID");
	String bankID=request.getParameter("bankID");
	String able="disabled";
	String ccbable="";
	String filter=" ";
	FirmValue firmer=null;
	FirmInfo info=null;
	String msg="";
	String submitFlag =request.getParameter("submitFlag");
	if(submitFlag!=null&&"do".equals(submitFlag)){
		firmer=new FirmValue();
		info=new FirmInfo();
		firmer.firmName=request.getParameter("accountName");
		firmer.cardType=request.getParameter("cardType");
		firmer.card=request.getParameter("card");
		firmer.firmID=request.getParameter("firmID");
		info.value=request.getParameter("ccbvalue");
		info.bankid=CCBbankID;
		firmer.firminfo=info;
		
		able=request.getParameter("able");
		int result =cp.modfirmuser(firmer,CCBbankID);
			if(result==-1){
			msg="修改信息失败";
			%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg %>');
				</SCRIPT>	
		<%			
		}else if(result==0){
			msg="修改信息成功";
			able="disabled='disabled'";
		%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg %>');
				window.returnValue="1";
				window.close();
				</SCRIPT>	
		<%
			
		}		
	}else{
		firmer=new FirmValue();
		info=new FirmInfo();
		info.key="CCBAc";
		info.bankid=CCBbankID;
		firmer.firmID=firmID;
		firmer.firminfo=info;
		firmer=cp.getFirmValue(firmer);	
		filter+="and firmid='"+firmID+"' and isopen=1";
		Vector<CorrespondValue> dicList =cp.getCorrespondList(filter);
		if(dicList.size()>0){
		for(CorrespondValue cv:dicList){
				if(cv.bankID.equals(CCBbankID)){
					ccbable="disabled='disabled'";
					break;
				}
				ccbable="";
			}
			able="disabled='disabled'";
		}else{
			able="";
		}
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>修改会员信息</title>
  </head>

  <body style="overflow-y: hidden" >
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:355;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;修改会员信息</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="100">
						<input name="firmID" disabled='disabled' value="<%=firmer.firmID%>" readonly type="hidden"  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">交易账号：&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=firmer.contact%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr id="ccbs" height="35" >				
					<td align="right">建行银行账号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('ccbvalue');"  onfocus="myfocus('ccbvalue')" id="ccbvalue" name="ccbvalue" value="<%=Tool.delNull(firmer.firminfo.value)%>" type=text class="text" maxlength="30"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="ccbvalueTip" class=""></div></td>			
					</tr>				<tr height="35">
					<td align="right">账户名称：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');"  <%=able%> onfocus="myfocus('accountName')" id="accountName" name="accountName" value="<%=Tool.delNull(firmer.firmName)%>" type=text  class="text" maxlength="128"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="accountNameTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select onblur="myblur('cardType');" onfocus="myfocus(cardType)" id="cardType" name="cardType"  <%=able%>  style="width: 140px" onchange="myblur('card')">
							<option value="">其他证件</option>
							<option value="1" <%=(firmer.cardType!=null && firmer.cardType.equals("1")) ? "selected" : ""%>>身份证</option>
							<option value="8" <%=(firmer.cardType!=null && firmer.cardType.equals("8")) ? "selected" : ""%>>组织机构代码</option>
							<option value="9" <%=(firmer.cardType!=null && firmer.cardType.equals("9")) ? "selected" : ""%>>营业执照</option>
							<option value="a" <%=(firmer.cardType!=null && firmer.cardType.equals("a")) ? "selected" : ""%>>法人代码证</option>
						</select><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><input type="checkbox" name="CheckCard" id="CheckCard" checked  onclick="updateCheckCard(this)"></input>验证证件号码</td>
				</tr>
				<tr height="35">
					<td align="right">证件号码：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('card');" onfocus="myfocus(card)" id="card" name="card"  <%=able%> value="<%=Tool.delNull(firmer.card)%>" type=text  class="text" maxlength="18"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="cardTip" class=""></div></td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="sub_btn" class="btn_sec" onclick="doAdd();" >修改</button>&nbsp;
					<button id="bak_btn" class="btn_cz" onclick="window.close();">返回</button>&nbsp;
					<input type=hidden name=submitFlag value="">
					<input type=hidden name=accountName value="<%=Tool.delNull(firmer.firmName)%>">
					<input type=hidden name=cardType value="<%=Tool.delNull(firmer.cardType)%>">
					<input type=hidden name=card value="<%=Tool.delNull(firmer.card)%>">
					<input type=hidden name=ccbvalue value="<%=Tool.delNull(firmer.firminfo.value)%>">	
					<input type=hidden name=able value="<%=able%>">				
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
var isCheckCard=true;
var ErrorClass = "onError";
var CorrectClass = "";//"onCorrect";
var FocusClass = " ";
var FocusMsg = " ";
var ErrorMsg = "验证错误";
var SuccessMsg = " ";

function updateCheckCard(CheckCard){
	if(CheckCard.checked){
		isCheckCard=true;
		myblur('card');
	}else{
		isCheckCard=false;
		idobj("cardTip").innerHTML=" ";
		tipclass("card",true);
		idobj("card"+"Tip").innerHTML = " ";
	}
}
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
	if("ccbvalue"==userID){
		flag = account(userID);
	}else if("card"==userID&&isCheckCard){
		flag=card("cardType",userID)
	}else if("accountName"==userID){
		notnull("accountName");
	}else if("all"==userID){
		if(!account("ccbvalue")) flag = false;
		if(isCheckCard&&!card("cardType","card")) flag = false;
		if(!notnull("accountName")) flag = false;		
	}else{
		//flag=false;
	}
	return flag;
}
function account1(userID){
	var user = idobj(userID);
	var isOpen = idobj("isOpen").value;
	var bankID = idobj("bankID").value;
	var tip = idobj(userID+"Tip");
	var flag = true;
	if(isOpen==1 && bankID=="005"){
		if(isEmpty(user.value)){
			tip.innerHTML = "不能为空";
			flag = false;
		}else if(!is_Str(user.value,false,null)){
			tip.innerHTML = "只能输入数字或字母";
			flag = false;
		}else{
			tip.innerHTML = SuccessMsg;
		}
	}else{
		tip.innerHTML = SuccessMsg;
	}
	tipclass(userID,flag);
	return flag;
}
function accountName(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML = "不能为空";
	}else if(!is_Str(user.value,true,null)){
		tip.innerHTML="只能输入数字、字母和汉字";
	}else{
		tip.innerHTML=SuccessMsg;
		flag = true;
	}
	tipclass(userID,flag);
	return flag;
}

function notnull(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(isEmpty(user.value)){
		tip.innerHTML="不能为空";
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
function doAdd(){
	var flag = true;
	flag = myblur("all");
	if(flag){
		if(confirm("您确定修改账户信息吗？")){
			idobj("sub_btn").disabled = 'disabled';
			idobj("bak_btn").disabled = 'disabled';
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

function card(type,cardvalue){
var flag=false;
var cardType=idobj(type);
var Card=idobj(cardvalue);
	if (isEmpty(Card.value+"")) {
			idobj(cardvalue+"Tip").innerHTML = "不能为空";
	} else if (!isStr(Card.value, null, new Array('-'))) {
			idobj(cardvalue+"Tip").innerHTML = "不能输入不合法字符";
	} else if (Card.value.length > 18 || Card.value.length < 6) {
			idobj(cardvalue+"Tip").innerHTML = "长度不能小于6位且不能超过18位";
	}else {
			idobj(cardvalue+"Tip").innerHTML=SuccessMsg;
		flag=true;
	}
	if(cardType.value=="1"){
		flag=is_CardID(Card.value);
		idobj(cardvalue+"Tip").innerHTML = "身份证验证不通过";
	}
	tipclass(cardvalue,flag);
	//idobj(cardvalue+"Tip").innerHTML = " ";
	return flag;
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
function selecbank(){
	if(idobj("bank").value=='013'){
		idobj("ccbs").style.display = "block";
	}else{
		idobj("ccbs").style.display = "none";
	}
}
function myfocus(){
}
function checkbank(bank){
var bankid=idobj(bank).value;
var tip=idobj(bank+"Tip");
	if(bankid=='-1'){
		tip.innerHTML="请选择银行";
		tipclass(bank,false);
		return false;
	}else{
		tip.innerHTML=SuccessMsg;
		tipclass(bank,true);
		return true;
	}
}
</SCRIPT>