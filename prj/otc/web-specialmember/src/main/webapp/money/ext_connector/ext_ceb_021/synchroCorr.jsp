<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%@ page import="java.rmi.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>

<base target="_self"> 

<%
    String bankID = request.getParameter("bankID");
	CEBBankDao dao = BankDAOFactory.getCEBDAO();
	CEBCapitalProcessorRMI cp = null;
	try{
		cp = getCEBBankUrl("021");
	}catch(Exception e){
		e.printStackTrace();
	}
	String firmID = request.getParameter("firmID");
	String checkSignHX = request.getParameter("checkSignHX");
	CorrespondValue corr = new CorrespondValue();
	Vector<CorrespondValue> vc = dao.getCorrespondList(" and firmid = '" + firmID + "' ");
	CorrespondValue sv = null;
	FirmValue firmValue = dao.getFirm(firmID);
	corr.firmID = firmValue.firmID;
	corr.cardType = firmValue.cardType;
	if("1".equals(firmValue.cardType)){
		corr.accountProp = "1";
	}
	corr.card = firmValue.card;
	corr.contact = firmValue.contact;
	corr.accountName = firmValue.firmName;
	if("do".equals(request.getParameter("submitFlag"))){
		String RelatingAcct = Tool.delNull(request.getParameter("RelatingAcct")).trim();
		bankID = request.getParameter("bankID");
		corr.bankID = "021";
		String str = "交易账号管理-光大银行签约-";
		ReturnValue result = null;
		corr.isCrossline = "0";//判断是否是他行签约的字段信息
		corr.account = RelatingAcct;
		corr.bankName=Tool.delNull(request.getParameter("OpenBankName")).trim();
		result = cp.synchroAccount(corr);
			
		String msg = "";
		
		if(result.result < 0){
			
			msg = "光大银行签约["+corr.firmID+"]失败,"+result.remark;
			
			%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg %>');
			</SCRIPT>
			<%
		}else{
			msg = "光大银行预签约："+corr.firmID+"成功";
			
			%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg%>');
				window.returnValue="1";
				window.close();
			</SCRIPT>	
			<%
			return;
		} 
	}
%>


<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../../css/report.css" type="text/css"/>
	<script language="javascript" src="../../lib/tools.js"></script>
    <title>光大银行签约信息</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:400;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><!--img src="/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" /-->&nbsp;光大银行签约信息</div>
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
						<input name="frimName" disabled='disabled' readonly value="<%=corr.accountName%>" type=text  class="input_text" maxlength="10" style="width: 140px">
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
								//if(bank.bankName.equals("光大银行")){
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
					<td align="right">开户行全称：&nbsp;</td>
					<td align="left">
						<input id="OpenBankName" name="OpenBankName" value="" type=text  maxlength="100" style="width: 150px"><span>*</span>	
					</td>
				</tr>
				<tr height="35">
					<td align="right">账号(卡号)：&nbsp;</td>
					<td align="left">
						<input id="RelatingAcct" name="RelatingAcct" value="" type=text  maxlength="32" style="width: 150px"><span>*</span>	
					</td>
				</tr>
				<tr height="35">
					<td align="right">证件类型：&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType"   style="width: 140px">
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
						<input id="card" name="card"  value="<%=Tool.delNull(corr.card)%>" type=text  class="text" maxlength="18"  style="width: 140px">
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
					if("1".equals(checkSignHX)){
						%>
						<button disabled="disabled" id="sub_btn" class="smlbtn" onclick="doAdd();">签约</button>&nbsp;
						<%
					}else{
						%>
						<input type="button" id="sub_btn" class="smlbtn" onclick="doAdd();" value="签约"></input>&nbsp;
					
						
						<input type=hidden name="checkSignHX" value = "Y">
						<%
					}
					
					%>
					<input type="button" id="bak_btn" class="smlbtn" onclick="window.close();" value="返回"></input>&nbsp;
					
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

    if(idobj("OpenBankName").value==null || idobj("RelatingAcct").value=="" || isNaN(idobj("RelatingAcct").value)){
			alert("请输入正确卡号或者开户行名称");
			return false;
	}
	var flag = true;
	if(flag){
		//alert(idobj("bankID").value);
		if(confirm("您确定签约账户信息吗？")){
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
