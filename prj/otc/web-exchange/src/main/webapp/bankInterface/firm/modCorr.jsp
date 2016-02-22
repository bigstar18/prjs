<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String ID = request.getParameter("ID");
	CorrespondValue corr = dao.getCorrespondList(" and ID="+ID).get(0);
	if("do".equals(request.getParameter("submitFlag"))){
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		lv.setLogtype("2110");
		lv.setLogoprtype("E");
		int isOpen = Integer.parseInt(request.getParameter("isOpen"));
		String bankID = request.getParameter("bankID");
		String account = request.getParameter("account");
		String account1 = request.getParameter("account1");
		String befch= "(银行["+corr.bankID+"]签约状态["+corr.isOpen+"]银行账号["+corr.account+"]银行记录账号["+corr.account1+"])";
		corr.isOpen = isOpen;
		corr.bankID = bankID;
		corr.account=account;
		corr.account1 = account1;

		String str = "交易账号管理-强制修改-";
		String aftch= "(银行["+bankID+"]签约状态["+isOpen+"]银行账号["+account+"]银行记录账号["+account1+"])";
		str += "修改前信息:"+befch+";修改后信息:"+aftch;
		if(corr.isOpen != 1){
			corr.status = 1;
			if(checkAccount(bankID) && (corr.account==null ||corr.account.trim().length()<=0)){
				corr.account = Tool.getConfig("DefaultAccount");
			}
		}
		ReturnValue result = null;
		try {
			CapitalProcessorRMI cp = null;
			try{
				//多处理器路由
				//cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
				cp = getBankUrl(bankID);
			}catch(Exception e){
				e.printStackTrace();
			}
			result = cp.modAccountMarket(corr,corr);
		} catch(Exception e) {
			e.printStackTrace();
			result = new ReturnValue();
			result.result = -1;
			result.remark = "系统异常";
		}
		String msg = "";
		if(result == null){
			ErrorCode errorcode = new ErrorCode();
			errorcode.load();
			msg = "修改交易账号["+corr.firmID+"]未接收到后台返回";
			lv.setResult(1);
			lv.contentvalue = "当前信息不能确定";
			lv.setLogcontent(str+"-"+msg);
			dao.log(lv);
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//-->
				</SCRIPT>	
			<%
		}else if(result.result >= 0) {
			msg = "修改交易账号："+corr.firmID+"成功";
			lv.setResult(0);
			lv.contentvalue = "当前信息"+aftch;
			lv.setLogcontent(str+"-"+msg);
			dao.log(lv);
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg%>');
				window.returnValue="1";
				window.close();
				//-->
			</SCRIPT>	
			<%
			return;
		} else {
			ErrorCode errorcode = new ErrorCode();
			errorcode.load();
			String rs = "";
			if(result.remark != null && result.remark.trim().length()>0){
				rs = result.remark;
			}else if(ErrorCode.error.get(result.result) != null){
				rs = ErrorCode.error.get(result.result);
			}else{
				rs = "后台返回码["+result.result+"]";
			}
			msg = "修改交易账号["+corr.firmID+"]"+rs;
			lv.setResult(1);
			lv.contentvalue = "当前信息"+befch;
			lv.setLogcontent(str+"-"+msg);
			dao.log(lv);
			%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=msg %>');
				//-->
				</SCRIPT>	
			<%
		}
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>强制修改账户信息</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
  	<input type='hidden' id='ID' name='ID' value='<%=ID%>'>
	<div style="overflow:auto;height:355;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;强制修改账户信息</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="100">
						<input name="firmID" disabled='disabled' value="<%=corr.firmID%>" readonly type="hidden"  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>：&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=corr.contact%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">签约状态：&nbsp;</td>
					<td align="left">
						<select id="isOpen" name="isOpen" class="normal" style="width: 140px">
							<option value="0" <%= corr.isOpen==0 ? "selected" : ""%>>未签约</option>
							<option value="1" <%= corr.isOpen==1 ? "selected" : ""%>>已签约</option>
							<option value="2" <%= corr.isOpen==2 ? "selected" : ""%>>已解约</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">签约银行：&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList(" ");
						for(int i=0;i<bankList.size();i++){
							BankValue bank = (BankValue)bankList.get(i);
							out.print("<input type='hidden' id='"+bank.bankID+"' name='"+bank.bankID+"' value='"+bank.bankName+"'>");
						}
						%>
						<select onblur="myblur('bankID');" id="bankID" name="bankID" class="normal" style="width: 140px">
								<option value="">无银行</option>
							<%
							for(int i=0;i<bankList.size();i++){
								BankValue bank = (BankValue)bankList.get(i);
								%>
								<option value="<%=bank.bankID%>" <%= bank.bankID.equals(corr.bankID) ? "selected" : ""%>><%=bank.bankName%></option>
								<%
							}
							if(corr.bankID != null && corr.bankID.trim().length()>0){
								
								%>
								<script>
									frm.bankID.value='<%=corr.bankID%>';
									frm.bankID.disabled='disabled';
								</script>
								<input type='hidden' id="bankID" name = "bankID" value='<%=corr.bankID%>'>
								<%
							}
							%>
						</select>
					</td>
					<td><div id="bankIDTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">银行账号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account');" onfocus="myfocus(account)" id="account" name="account" value="<%=Tool.delNull(corr.account)%>" type=text class="text" maxlength="30"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="accountTip" class=""></div></td>
				</tr>
				<tr height="35" id="account1tr">
					<td align="right">农行客户编号：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account1');" onfocus="myfocus(account1)" id="account1" name="account1" value="<%=Tool.delNull(corr.account1)%>" type=text class="text" maxlength="30"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="account1Tip" class="">&nbsp;</div></td>
				</tr>
				<tr height="35">
					<td align="right">账户名称：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');" disabled="disabled" onfocus="myfocus(accountName)" id="accountName" name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="text" maxlength="128"  style="width: 140px">
					</td>
					<td><div id="accountNameTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select onblur="myblur('cardType');" onfocus="myfocus(cardType)" id="cardType" name="cardType" disabled  style="width: 140px">
							<option value="">其他证件</option>
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
						<input onblur="myblur('card');" onfocus="myfocus(card)" id="card" name="card" readonly disabled='disabled' value="<%=Tool.delNull(corr.card)%>" type=text  class="text" maxlength="18"  style="width: 140px">
					</td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">					
				<td align="center" colspan=2>
					<button id="sub_btn" class="btn_sec" onclick="doAdd();">修改</button>&nbsp;
					<button id="bak_btn" class="btn_cz" onclick="window.close();">返回</button>&nbsp;
					<input type=hidden name=submitFlag value="">
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
<script>
	if(document.getElementById("bankID").value=="005"){
		document.getElementById("account1tr").style.display = "inline";
	}else{
		document.getElementById("account1tr").style.display = "none";
	}
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
var ErrorClass = "onError";
var CorrectClass = "";//"onCorrect";
var FocusClass = " ";
var FocusMsg = " ";
var ErrorMsg = "验证错误";
var SuccessMsg = " ";
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
	}else if ("account1"==userID){
		flag = account1(userID);
	}else{
		if(!bankID("bankID")) flag = false;
		if(!account("account")) flag = false;
		if(!account1("account1")) flag = false;
	}
	return flag;
}
function bankID(userID){
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	var account1 = idobj("account1");
	var account1tr = idobj("account1tr");
	var flag = true;
	if(user.value=="005"){
		account1tr.style.display = "inline";
		//account1.value='<%//=Tool.delNull(corr.account1)%>';
		tip.innerHTML = SuccessMsg;
	}else{
		if(user.value==""){
			flag = false;
			tip.innerHTML = "请选择银行";
		}else{
			tip.innerHTML = SuccessMsg;
		}
		account1tr.style.display = "none";
	}
	tipclass(userID,flag);
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
function account(userID){
	var user = idobj(userID);
	var bankID = idobj("bankID");
	var tip = idobj(userID+"Tip");
	var flag = false;
	if(bankID.value != "005" && isEmpty(user.value)){
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
		//alert(idobj("bankID").value);
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