<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self"> 
<%
String bankID = request.getParameter("bankID");
BankDAO dao = BankDAOFactory.getDAO();
BankValue bank = dao.getBank(bankID);
if(bank == null) {
	out.println("���в����ڣ�");
	return;
}
int con = bank.control;
if("do".equals(request.getParameter("submitFlag"))){
	CapitalProcessorRMI cp = null;
	try {
		//�ദ�����޸�
		//cp = (CapitalProcessorRMI)Naming.lookup("//" + RmiIpAddress + ":" + RmiPortNumber + "/"+ RmiServiceName);
	    cp = getBankUrl(bankID);
	} catch(Exception e) {
		e.printStackTrace();
	}
	LogValue lv = new LogValue();
	lv.setLogopr(AclCtrl.getLogonID(request));
	lv.setLogtime(new Date());
	lv.setIp(computerIP);
	lv.setLogtype("2130");
	lv.setLogoprtype("E");
	bank.validFlag = Tool.strToInt(request.getParameter("validFlag"));
	bank.inMoneyFlag = Tool.strToInt(request.getParameter("inMoneyFlag"));
	bank.outMoneyFlag = Tool.strToInt(request.getParameter("outMoneyFlag"));
	bank.beginTime = request.getParameter("beginTime").trim();
	bank.endTime = request.getParameter("endTime").trim();
	
	int oldvalidFlag = Tool.strToInt(request.getParameter("validFlag"));
	int oldinMoneyFlag = Tool.strToInt(request.getParameter("inMoneyFlag"));
	int oldoutMoneyFlag = Tool.strToInt(request.getParameter("outMoneyFlag"));
	String oldbeginTime = request.getParameter("beginTime").trim();
	String oldendTime = request.getParameter("endTime").trim();
	String str = "����״̬����-����״̬�޸�-";
	String befch = "(����״̬["+oldvalidFlag+"]���״̬["+oldinMoneyFlag+"]����״̬["+oldoutMoneyFlag+"]��ʼʱ��["+oldbeginTime+"]����ʱ��["+oldendTime+"])";
	String aftch = "(����״̬["+bank.validFlag+"]���״̬["+bank.inMoneyFlag+"]����״̬["+bank.outMoneyFlag+"]��ʼʱ��["+bank.beginTime+"]����ʱ��["+bank.endTime+"])";
	str += "�޸�ǰ��Ϣ"+befch+"�޸ĺ���Ϣ"+aftch;
	long result = 0;
	try {
		result = cp.modBank(bank).result;
	} catch(Exception e) {
		e.printStackTrace();
		result = -1;
	}
	if(result >= 0) {
		lv.setResult(0);
		lv.contentvalue = "��ǰ��Ϣ"+aftch;
		lv.setLogcontent(str+"-�޸�"+bank.bankName+"���óɹ�");
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("�����޸ĳɹ���");
			window.returnValue="1";
			window.close();
			//-->
			</SCRIPT>	
		<%
		dao.log(lv);
		return;
	} else {
		lv.setResult(1);
		lv.contentvalue = "��ǰ��Ϣ"+befch;
		lv.setLogcontent(str+"-�޸�"+bank.bankName+"����ʧ��");
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("�����޸�ʧ�ܣ�");
			//-->
			</SCRIPT>	
		<%
	}
	dao.log(lv);
}
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../lib/tools.js"></script>
	<link type="text/css" rel="stylesheet" href="../lib/jquery/style/validator.css"></link>
    <title>�ӿ�״̬����</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:400;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;�ӿ�״̬����</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35">
					<td align="right" width="25%">���д��룺&nbsp;</td>
					<td align="left" width="30%">
						<input name="bankID" style="width:120px;" value="<%=bank.bankID%>" readonly  disabled='disabled' type=text  class="input_text" maxlength="10">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">�������ƣ�&nbsp;</td>
					<td align="left">
						<input name="bankName" style="width:120px;" value="<%=bank.bankName%>" readonly  disabled='disabled' type=text  class="input_text" maxlength="10">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">����״̬��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "0" <%=bank.validFlag==0 ? "checked='checked'" : ""%> name = "validFlag" />����
						&nbsp;&nbsp;
						<input type = "radio" value = "1" <%=bank.validFlag==1 ? "checked='checked'" : ""%> name = "validFlag" />����
						<input type = "hidden" name="oldvalidFlag" value="<%=bank.validFlag%>">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">���״̬��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "0" <%=bank.inMoneyFlag==0 ? "checked='checked'" : ""%> name = "inMoneyFlag" />����
						&nbsp;&nbsp;
						<input type = "radio" value = "1" <%=bank.inMoneyFlag==1 ? "checked='checked'" : ""%> name = "inMoneyFlag" />����
						<input type="hidden" name="oldinMoneyFlag" value="<%=bank.inMoneyFlag%>">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">����״̬��&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "0" <%=bank.outMoneyFlag==0 ? "checked='checked'" : ""%> name = "outMoneyFlag" />����
						&nbsp;&nbsp;
						<input type = "radio" value = "1" <%=bank.outMoneyFlag==1 ? "checked='checked'" : ""%> name = "outMoneyFlag" />����
						<input type="hidden" name="oldoutMoneyFlag" value="<%=bank.outMoneyFlag%>">
					</td>
					<td>&nbsp;</td>
				</tr>
				<!-- <tr height="35">
					<td align="right">ת�����ƣ�&nbsp;</td>
					<td align="left">
						<select name="control">
							<option <%=con==0 ? "selected" : ""%> value="0">����ʱ��˫����</option>
							<option <%=con==2 ? "selected" : ""%> value="2">ֻ����������</option>
							<option <%=con==3 ? "selected" : ""%> value="3">ֻ��ʱ������</option>
							<option <%=con==1 ? "selected" : ""%> value="1">������</option>
						</select>&nbsp;<span class=star>*</span>
					</td>
					<td>&nbsp;</td>
				</tr> -->
				<tr height="35">
					<td align="right">ת�˿�ʼʱ�䣺&nbsp;</td>
					<td align="left">
						<input onblur="myblur('beginTime');" onfocus="myfocus('beginTime')" id="beginTime" name="beginTime" style="width:120px;" maxlength="8" value="<%=bank.beginTime%>"><span class=star><font color='#FF0000'>*</font></span>
						<input type="hidden" name="oldbeginTime" value="<%=bank.beginTime%>">
					</td>
					<td><div id="beginTimeTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">ת�˽���ʱ�䣺&nbsp;</td>
					<td align="left">
						<input onblur="myblur('endTime');" onfocus="myfocus('endTime')" id="endTime" name="endTime" style="width:120px;" maxlength="8" value="<%=bank.endTime%>"><span class=star><font color='#FF0000'>*</font></span>
						<input type="hidden" name="oldbeginTime" value="<%=bank.beginTime%>">
					</td>
					<td><div id="endTimeTip" class=""></div></td>
				</tr>
			</table>
			</td>
			</tr>
			</table>
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			<tr height="35">
				<td align="center" colspan=2>
					<button  class="btn_sec" onclick="doMod();">�޸�</button>&nbsp;
					<button class="btn_cz" onclick="window.close();">����</button>&nbsp;
					<input type=hidden name=submitFlag value="">
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doMod() {
	if(!myblur("all")) {
	} else {
		if(confirm("��ȷ���޸�����״̬��")){
			frm.submitFlag.value = "do";
			frm.submit();
		}
	}
}
function myblur(userID){
	var flag = true;
	if("beginTime"==userID || "endTime"==userID){
		flag = fmtTime(userID);
	}else{
		if(!fmtTime("beginTime")) flag = false;
		if(!fmtTime("endTime")) flag = false;
	}
	return flag;
}
function myfocus(userID){
}
function fmtTime(userID){
	var user = document.getElementById(userID);
	var tip = document.getElementById(userID+"Tip");
	var flag = false;
	if(isDateTime("1900-01-01 "+user.value)){
		tip.innerHTML = "";
		flag = true;
	}else{
		tip.innerHTML = "������(HH:mm:ss)��ʽ��ʱ��";
	}
	if(flag){
		tip.className="";
	}else{
		tip.className="onError";
	}
	return flag;
}
function isDateTime(str){
	var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r = str.match(reg); 
	if(r==null) return false; 
	var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]); 
	return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]);
}
//-->
</SCRIPT>