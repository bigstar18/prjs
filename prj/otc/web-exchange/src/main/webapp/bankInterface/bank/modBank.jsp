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
	out.println("银行不存在！");
	return;
}
int con = bank.control;
if("do".equals(request.getParameter("submitFlag"))){
	CapitalProcessorRMI cp = null;
	try {
		//多处理器修改
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
	String str = "银行状态管理-银行状态修改-";
	String befch = "(银行状态["+oldvalidFlag+"]入金状态["+oldinMoneyFlag+"]出金状态["+oldoutMoneyFlag+"]起始时间["+oldbeginTime+"]结束时间["+oldendTime+"])";
	String aftch = "(银行状态["+bank.validFlag+"]入金状态["+bank.inMoneyFlag+"]出金状态["+bank.outMoneyFlag+"]起始时间["+bank.beginTime+"]结束时间["+bank.endTime+"])";
	str += "修改前信息"+befch+"修改后信息"+aftch;
	long result = 0;
	try {
		result = cp.modBank(bank).result;
	} catch(Exception e) {
		e.printStackTrace();
		result = -1;
	}
	if(result >= 0) {
		lv.setResult(0);
		lv.contentvalue = "当前信息"+aftch;
		lv.setLogcontent(str+"-修改"+bank.bankName+"配置成功");
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("银行修改成功！");
			window.returnValue="1";
			window.close();
			//-->
			</SCRIPT>	
		<%
		dao.log(lv);
		return;
	} else {
		lv.setResult(1);
		lv.contentvalue = "当前信息"+befch;
		lv.setLogcontent(str+"-修改"+bank.bankName+"配置失败");
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("银行修改失败！");
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
    <title>接口状态设置</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:400;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;接口状态设置</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr height="35">
					<td align="right" width="25%">银行代码：&nbsp;</td>
					<td align="left" width="30%">
						<input name="bankID" style="width:120px;" value="<%=bank.bankID%>" readonly  disabled='disabled' type=text  class="input_text" maxlength="10">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">银行名称：&nbsp;</td>
					<td align="left">
						<input name="bankName" style="width:120px;" value="<%=bank.bankName%>" readonly  disabled='disabled' type=text  class="input_text" maxlength="10">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">银行状态：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "0" <%=bank.validFlag==0 ? "checked='checked'" : ""%> name = "validFlag" />可用
						&nbsp;&nbsp;
						<input type = "radio" value = "1" <%=bank.validFlag==1 ? "checked='checked'" : ""%> name = "validFlag" />禁用
						<input type = "hidden" name="oldvalidFlag" value="<%=bank.validFlag%>">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">入金状态：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "0" <%=bank.inMoneyFlag==0 ? "checked='checked'" : ""%> name = "inMoneyFlag" />可用
						&nbsp;&nbsp;
						<input type = "radio" value = "1" <%=bank.inMoneyFlag==1 ? "checked='checked'" : ""%> name = "inMoneyFlag" />禁用
						<input type="hidden" name="oldinMoneyFlag" value="<%=bank.inMoneyFlag%>">
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr height="35">
					<td align="right">出金状态：&nbsp;</td>
					<td align="left">
						<input type = "radio" value = "0" <%=bank.outMoneyFlag==0 ? "checked='checked'" : ""%> name = "outMoneyFlag" />可用
						&nbsp;&nbsp;
						<input type = "radio" value = "1" <%=bank.outMoneyFlag==1 ? "checked='checked'" : ""%> name = "outMoneyFlag" />禁用
						<input type="hidden" name="oldoutMoneyFlag" value="<%=bank.outMoneyFlag%>">
					</td>
					<td>&nbsp;</td>
				</tr>
				<!-- <tr height="35">
					<td align="right">转账限制：&nbsp;</td>
					<td align="left">
						<select name="control">
							<option <%=con==0 ? "selected" : ""%> value="0">日期时间双限制</option>
							<option <%=con==2 ? "selected" : ""%> value="2">只受日期限制</option>
							<option <%=con==3 ? "selected" : ""%> value="3">只受时间限制</option>
							<option <%=con==1 ? "selected" : ""%> value="1">不限制</option>
						</select>&nbsp;<span class=star>*</span>
					</td>
					<td>&nbsp;</td>
				</tr> -->
				<tr height="35">
					<td align="right">转账开始时间：&nbsp;</td>
					<td align="left">
						<input onblur="myblur('beginTime');" onfocus="myfocus('beginTime')" id="beginTime" name="beginTime" style="width:120px;" maxlength="8" value="<%=bank.beginTime%>"><span class=star><font color='#FF0000'>*</font></span>
						<input type="hidden" name="oldbeginTime" value="<%=bank.beginTime%>">
					</td>
					<td><div id="beginTimeTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">转账结束时间：&nbsp;</td>
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
					<button  class="btn_sec" onclick="doMod();">修改</button>&nbsp;
					<button class="btn_cz" onclick="window.close();">返回</button>&nbsp;
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
		if(confirm("您确定修改银行状态吗？")){
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
		tip.innerHTML = "请输入(HH:mm:ss)格式的时间";
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