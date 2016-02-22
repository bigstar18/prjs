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
		String befch= "(����["+corr.bankID+"]ǩԼ״̬["+corr.isOpen+"]�����˺�["+corr.account+"]���м�¼�˺�["+corr.account1+"])";
		corr.isOpen = isOpen;
		corr.bankID = bankID;
		corr.account=account;
		corr.account1 = account1;

		String str = "�����˺Ź���-ǿ���޸�-";
		String aftch= "(����["+bankID+"]ǩԼ״̬["+isOpen+"]�����˺�["+account+"]���м�¼�˺�["+account1+"])";
		str += "�޸�ǰ��Ϣ:"+befch+";�޸ĺ���Ϣ:"+aftch;
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
				//�ദ����·��
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
			result.remark = "ϵͳ�쳣";
		}
		String msg = "";
		if(result == null){
			ErrorCode errorcode = new ErrorCode();
			errorcode.load();
			msg = "�޸Ľ����˺�["+corr.firmID+"]δ���յ���̨����";
			lv.setResult(1);
			lv.contentvalue = "��ǰ��Ϣ����ȷ��";
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
			msg = "�޸Ľ����˺ţ�"+corr.firmID+"�ɹ�";
			lv.setResult(0);
			lv.contentvalue = "��ǰ��Ϣ"+aftch;
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
				rs = "��̨������["+result.result+"]";
			}
			msg = "�޸Ľ����˺�["+corr.firmID+"]"+rs;
			lv.setResult(1);
			lv.contentvalue = "��ǰ��Ϣ"+befch;
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
    <title>ǿ���޸��˻���Ϣ</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
  	<input type='hidden' id='ID' name='ID' value='<%=ID%>'>
	<div style="overflow:auto;height:355;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;ǿ���޸��˻���Ϣ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="100">
						<input name="firmID" disabled='disabled' value="<%=corr.firmID%>" readonly type="hidden"  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>��&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=corr.contact%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ״̬��&nbsp;</td>
					<td align="left">
						<select id="isOpen" name="isOpen" class="normal" style="width: 140px">
							<option value="0" <%= corr.isOpen==0 ? "selected" : ""%>>δǩԼ</option>
							<option value="1" <%= corr.isOpen==1 ? "selected" : ""%>>��ǩԼ</option>
							<option value="2" <%= corr.isOpen==2 ? "selected" : ""%>>�ѽ�Լ</option>
						</select>
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ���У�&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList(" ");
						for(int i=0;i<bankList.size();i++){
							BankValue bank = (BankValue)bankList.get(i);
							out.print("<input type='hidden' id='"+bank.bankID+"' name='"+bank.bankID+"' value='"+bank.bankName+"'>");
						}
						%>
						<select onblur="myblur('bankID');" id="bankID" name="bankID" class="normal" style="width: 140px">
								<option value="">������</option>
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
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account');" onfocus="myfocus(account)" id="account" name="account" value="<%=Tool.delNull(corr.account)%>" type=text class="text" maxlength="30"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="accountTip" class=""></div></td>
				</tr>
				<tr height="35" id="account1tr">
					<td align="right">ũ�пͻ���ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('account1');" onfocus="myfocus(account1)" id="account1" name="account1" value="<%=Tool.delNull(corr.account1)%>" type=text class="text" maxlength="30"  style="width: 140px"><span class=star><font color="#FF0000">*</font></span>
					</td>
					<td><div id="account1Tip" class="">&nbsp;</div></td>
				</tr>
				<tr height="35">
					<td align="right">�˻����ƣ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('accountName');" disabled="disabled" onfocus="myfocus(accountName)" id="accountName" name="accountName" value="<%=Tool.delNull(corr.accountName)%>" type=text  class="text" maxlength="128"  style="width: 140px">
					</td>
					<td><div id="accountNameTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select onblur="myblur('cardType');" onfocus="myfocus(cardType)" id="cardType" name="cardType" disabled  style="width: 140px">
							<option value="">����֤��</option>
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
					<button id="sub_btn" class="btn_sec" onclick="doAdd();">�޸�</button>&nbsp;
					<button id="bak_btn" class="btn_cz" onclick="window.close();">����</button>&nbsp;
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
var ErrorMsg = "��֤����";
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
			tip.innerHTML = "��ѡ������";
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
			tip.innerHTML = "����Ϊ��";
			flag = false;
		}else if(!is_Str(user.value,false,null)){
			tip.innerHTML = "ֻ���������ֻ���ĸ";
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
		tip.innerHTML = "����Ϊ��";
	}else if(!is_Str(user.value,true,null)){
		tip.innerHTML="ֻ���������֡���ĸ�ͺ���";
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
function myfocus(){
}
function doAdd(){
	var flag = true;
	flag = myblur("all");
	if(flag){
		//alert(idobj("bankID").value);
		if(confirm("��ȷ���޸��˻���Ϣ��")){
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