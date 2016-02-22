<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMIhx"%>
<base target="_self"> 
<%!
StaticMsg staticMsghx = new  StaticMsg();
public CapitalProcessorRMIhx getBankUrl_HX(String bankID){
	CapitalProcessorRMIhx result = null;
	String procUrl = "";
	if(bankID != null && bankID.trim().length()>0){
		procUrl = staticMsghx.getBank(bankID).beleiveProcessor;
	}
	if(procUrl == null || procUrl.trim().length()<=0){
		procUrl = staticMsghx.getBankMap().values().iterator().next().beleiveProcessor;
	}
	System.out.println("procUrl===="+procUrl);
	try{
		result = (CapitalProcessorRMIhx)Naming.lookup(procUrl);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	return result;
}
%>
<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String checkSignHX = request.getParameter("checkSignHX");
	CorrespondValue corr = new CorrespondValue();
	FirmValue firmValue = dao.getFirm(firmID);
	corr.firmID = firmValue.firmID;
	corr.cardType = firmValue.cardType;
	corr.card = firmValue.card;
	corr.contact = firmValue.contact;
	corr.name = firmValue.firmName;
	if("do".equals(request.getParameter("submitFlag"))){
		LogValue lv = new LogValue();
		lv.setLogopr(AclCtrl.getLogonID(request));
		lv.setLogtime(new Date());
		lv.setIp(computerIP);
		lv.setLogtype("2110");
		lv.setLogoprtype("E");
		String bankID = request.getParameter("bankID");
		corr.bankID = bankID;

		String str = "交易账号管理-华夏银行预签约-";
		ReturnValue result = null;
		//long result = 0;
		try {
			CapitalProcessorRMIhx cp = null;
			//try{
				cp = getBankUrl_HX(bankID);
				result = cp.synchroAccount(corr);
			//}catch(Exception e){
			//}
			//result = cp.modAccountMarket(corr,corr);
			
		} catch(Exception e) {
			e.printStackTrace();
			result = new ReturnValue();
			result.result = -1;
			result.remark = "系统异常，请联系系统管理员";
		}
		String msg = "";
		if(result.result != 0){
			ErrorCode errorcode = new ErrorCode();
			errorcode.load();
			msg = "华夏银行预签约["+corr.firmID+"]未接收到后台返回";
			msg = result.remark;
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
		}else if(result.result == 0) {
			msg = "华夏银行预签约："+corr.firmID+"成功";
			lv.setResult(0);
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
		} 
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>华夏银行预签约信息</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:355;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;华夏银行预签约信息</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="100">
						<input name="firmID" disabled='disabled' value="<%=firmID%>" readonly type="hidden"  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>：&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=corr.contact%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">名称：&nbsp;&nbsp;</td>
					<td align="left">
						<input name="frimName" disabled='disabled' readonly value="<%=corr.name%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" style="display: none">
					<td align="right">签约银行：&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList("and bankid='006' ");
						%>
						<select id="bankID" name="bankID" class="normal" style="width: 140px">
								<!-- <option value="">无银行</option> -->
							<%
							for(int i=0;i<bankList.size();i++){
								BankValue bank = (BankValue)bankList.get(i);
								//if(bank.bankName.equals("华夏银行")){
								%>
								<option value="<%=bank.bankID%>" <%= "selected" %>><%=bank.bankName%></option>
								<%
							}
							%>
						</select>
					</td>
					<td><div id="bankIDTip" class=""></div></td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType" disabled="disabled"  style="width: 140px">
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
						<input id="card" name="card" readonly disabled="disabled" value="<%=Tool.delNull(corr.card)%>" type=text  class="text" maxlength="18"  style="width: 140px">
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
					<%
					if(checkSignHX.equals("Y")){
						%>
						<button disabled="disabled" id="sub_btn" class="btn_sec" onclick="doAdd();">同步</button>&nbsp;
						<%
					}else{
						%>
						<button id="sub_btn" class="btn_sec" onclick="doAdd();">同步</button>&nbsp;
						<%
					}

					%>
					<button id="bak_btn" class="btn_cz" onclick="window.close();">返回</button>&nbsp;
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
var CorrectClass = "";//"onCorrect";
var FocusClass = " ";
var FocusMsg = " ";
var ErrorMsg = "验证错误";
var SuccessMsg = " ";
function idobj(userID){
	return document.getElementById(userID);
}

function doAdd(){
	var flag = true;
	if(flag){
		//alert(idobj("bankID").value);
		if(confirm("您确定预签约账户信息吗？")){
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
</SCRIPT>
