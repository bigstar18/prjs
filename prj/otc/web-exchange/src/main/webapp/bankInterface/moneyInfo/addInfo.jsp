<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
	BankDAO dao = BankDAOFactory.getDAO();
	/* �ദ����·��
	CapitalProcessorRMI cp = null;
	try {
		cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	} catch(Exception e) {
		e.printStackTrace();
	}*/
	if("do".equals(request.getParameter("submitFlag"))){
		String contact = request.getParameter("contact");
		String bankID = request.getParameter("bankID");
		int type = Integer.parseInt(request.getParameter("type"));
		double money = Double.valueOf(request.getParameter("money"));
		String funID = request.getParameter("funID");
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		lv.setLogtype("2140");
		lv.setLogoprtype("E");
		ReturnValue result = new ReturnValue();
		result.result=-1;
		if(contact == null || contact.trim().length()<=0){
			result.remark = "����ǩԼ�˺Ų���Ϊ��";
		}else if(bankID == null || bankID.trim().equals("-1")){
			result.remark = "ѡ�������쳣";
		}else{
			String filter = " and isopen=1 and bankID='"+bankID.trim()+"'";
			//if(firmID != null && firmID.trim().length()>0){
				//filter += " and firmID='"+firmID.trim()+"'";
			//}
			if(contact != null && contact.trim().length()>0){
				filter += " and contact='"+contact.trim()+"'";
			}
			try{
				Vector<CorrespondValue> cvs = dao.getCorrespondList(filter);
				if(cvs == null || cvs.size()<=0){
					result.remark = "δ��ѯ�������˺Ű���Ϣ";
				}else{
					CorrespondValue cv = cvs.get(0);
					CapitalValue value = new CapitalValue();
					value.trader = "manual";
					value.funID = funID.trim();
					value.firmID = cv.firmID;
					value.contact = cv.contact;
					value.bankID = cv.bankID;
					value.type = type;
					value.launcher = ProcConstants.marketLaunch;
					value.money = money;
					value.status = ProcConstants.statusSuccess;
					value.bankTime = new java.sql.Timestamp(new java.util.Date().getTime());
					value.note = "�ֹ������";
					/* �ദ����·�� */
					CapitalProcessorRMI cp = null;
					try {
						cp = getBankUrl(value.bankID);
					} catch(Exception e) {
						e.printStackTrace();
					}
					//--�޸Ľ���----
					result = cp.supplyCapitalInfo(value);
					if(result.result>=0){
						result.remark += "�ɹ�";
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				result.remark = "ϵͳ�쳣";
			}
		}
%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				alert('<%=result.remark %>');
				//-->
			</SCRIPT>
<%
		if(result.result >=0){
		lv.setResult(0);
%>
			<SCRIPT LANGUAGE="JavaScript">
				<!--
				window.returnValue="0";
				window.close();
				//-->
			</SCRIPT>
<%
		}else{
			lv.setResult(1);
		}
		lv.setLogcontent("�����˴���-������ˮ-"+result.remark);
		dao.log(lv);
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
    <title>�ֹ���ˮ</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:250;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;�ֹ���ˮ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<!-- <tr height="35">
					<td align="right">�����˺ţ�&nbsp;</td>
					<td align="left">
						<input name="firmID" type=text  class="input_text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr> -->
				<tr height="35">
					<td align="right" width="18%">�ʽ��˺ţ�&nbsp;</td>
					<td align="left" width="100">
						<input onblur="myblur('contact');" onfocus="myfocus('contact')" id="contact" name="contact" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="contactTip" class="">&nbsp;</div></td>
				</tr>
				<tr height="35">
					<td align="right">���У�&nbsp;</td>
					<td align="left">
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
						</select><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="bankIDTip" class="">&nbsp;</div></td>
				</tr>
				<tr height="35">
					<td align="right">ת�����ͣ�&nbsp;</td>
					<td align="left">
						<select onblur="myblur('type');" onfocus="myfocus('type')" id="type" name="type" class="normal" style="width: 140px">
							<OPTION value="">��ѡ��</OPTION>
							<option value="<%=ProcConstants.inMoneyType%>">���</option>
							<option value="<%=ProcConstants.outMoneyType%>">����</option>
						</select><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="typeTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">ת�˽�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('money2');" onfocus="myfocus('money2')" id="money2" name="money" value="" type=text  class="input_text" maxlength="12" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
						<!-- <input name="money" type="hidden" value="0"> -->
					</td>
					<td><div id="money2Tip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">������ˮ�ţ�&nbsp;</td>
					<td align="left">
						<input onblur="myblur('funID');" onfocus="myfocus('funID')" id="funID" name="funID" value="" type=text  class="input_text" maxlength="30" style="width: 140px"><span class=star><font color='#FF0000'>*</font></span>
					</td>
					<td><div id="funIDTip" class=""></div></td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
	</div>
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
		<tr height="35">					
			<td align="center" colspan=2>
				<button  class="btn_sec" onclick="doAdd();">���</button>&nbsp;
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
String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function idobj(userID){
	return document.getElementById(userID);
}
function tipClass(userID,flag){
	var tip = idobj(userID+"Tip");
	if(flag){
		tip.className="";
	}else{
		tip.className="onError";
	}
}
function myblur(userID){
	var flag = true;
	if("contact"==userID){
		flag = contact(userID);
	}else if("bankID"==userID){
		flag = bankID(userID);
	}else if("type"==userID){
		flag = type(userID);
	}else if("money2"==userID){
		flag = money2(userID);
	}else if("funID"==userID){
		flag = funID(userID);
	}else{
		if(!contact("contact")) flag = false;
		if(!bankID("bankID")) flag = false;
		if(!type("type")) flag = false;
		if(!money2("money2")) flag = false;
		if(!funID("funID")) flag = false;
	}
	return flag;
}
function contact(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML = "������";
	}else if(!is_Str(user.value,null,null)){
		tip.innerHTML = "ֻ���������ֺ���ĸ";
	}else if(user.value.length>20){
		tip.innerHTML = "�����20λ";
	}else{
		tip.innerHTML = "";
		flag = true;
	}
	tipClass(userID,flag);
	return flag;
}
function bankID(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML = "��ѡ��";
	}else{
		tip.innerHTML = "";
		flag = true;
	}
	tipClass(userID,flag);
	return flag;
}
function type(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML = "��ѡ��";
	}else{
		tip.innerHTML = "";
		flag = true;
	}
	tipClass(userID,flag);
	return flag;
}
function money2(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML = "������";
	}else if(!isflote(user.value,2)){
		tip.innerHTML = "�����������λС��������";
	}else if(user.value>10000000){
		tip.innerHTML = "ת�˽��ܳ���10000000(һǧ��)";
	}else{
		tip.innerHTML = "";
		flag = true;
	}
	tipClass(userID,flag);
	return flag;
}
function funID(userID){
	var flag = false;
	var user = idobj(userID);
	var tip = idobj(userID+"Tip");
	if(isEmpty(user.value)){
		tip.innerHTML = "������";
	}else if(!is_Str(user.value,null,null)){
		tip.innerHTML = "ֻ���������ֺ���ĸ";
	}else if(user.value.length>30){
		tip.innerHTML = "���ʮλ";
	}else{
		tip.innerHTML = "";
		flag = true;
	}
	tipClass(userID,flag);
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
		if(confirm("��ȷ����Ӹ���ˮ��")){
			frm.submitFlag.value = "do";
			frm.submit();
		}
	}
}
function isEmpty(s){
	if(s.trim().length<=0){
		return true;
	}
	return false;
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
function isflote(s,n){
	if(isEmpty(s)){
		return true;
	}
	var matchs='\^\\+\?([1-9]{1}[0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	//var matchs='\^\\+\?([0-9]\*|0)(\\.[0-9]{1,'+n+'})\?\$';
	var patrn = new RegExp(matchs,"ig");
	if (patrn.exec(s)) {
		return true ;
	}
	return false;
}
//-->
</SCRIPT>