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
		String str = "�����˺Ź���-�������ǩԼ-";
		ReturnValue result = null;
		corr.isCrossline = "0";//�ж��Ƿ�������ǩԼ���ֶ���Ϣ
		corr.account = RelatingAcct;
		corr.bankName=Tool.delNull(request.getParameter("OpenBankName")).trim();
		result = cp.synchroAccount(corr);
			
		String msg = "";
		
		if(result.result < 0){
			
			msg = "�������ǩԼ["+corr.firmID+"]ʧ��,"+result.remark;
			
			%>
			<SCRIPT LANGUAGE="JavaScript">
				alert('<%=msg %>');
			</SCRIPT>
			<%
		}else{
			msg = "�������ԤǩԼ��"+corr.firmID+"�ɹ�";
			
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
    <title>�������ǩԼ��Ϣ</title>
  </head>
  
  <body style="overflow-y: hidden">
  	<form id="frm" action="" method="post">
	<div style="overflow:auto;height:400;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title"><!--img src="/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" /-->&nbsp;�������ǩԼ��Ϣ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="100">
						<input name="firmID" disabled='disabled' value="<%=firmID%>" readonly type="hidden"  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right"><%=CONTACTTITLE%>��&nbsp;</td>
					<td align="left">
						<input name="contact" disabled='disabled' readonly value="<%=corr.contact%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">���ƣ�&nbsp;&nbsp;</td>
					<td align="left">
						<input name="frimName" disabled='disabled' readonly value="<%=corr.accountName%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" style="display: none">
					<td align="right">ǩԼ���У�&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList("and bankid='006' ");
						%>
						<select id="bankID" name="bankID" class="normal" style="width: 140px">
								<!-- <option value="">������</option> -->
							<%
							for(int i=0;i<bankList.size();i++){
								BankValue bank = (BankValue)bankList.get(i);
								//if(bank.bankName.equals("�������")){
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
					<td align="right">������ȫ�ƣ�&nbsp;</td>
					<td align="left">
						<input id="OpenBankName" name="OpenBankName" value="" type=text  maxlength="100" style="width: 150px"><span>*</span>	
					</td>
				</tr>
				<tr height="35">
					<td align="right">�˺�(����)��&nbsp;</td>
					<td align="left">
						<input id="RelatingAcct" name="RelatingAcct" value="" type=text  maxlength="32" style="width: 150px"><span>*</span>	
					</td>
				</tr>
				<tr height="35">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType"   style="width: 140px">
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
						<button disabled="disabled" id="sub_btn" class="smlbtn" onclick="doAdd();">ǩԼ</button>&nbsp;
						<%
					}else{
						%>
						<input type="button" id="sub_btn" class="smlbtn" onclick="doAdd();" value="ǩԼ"></input>&nbsp;
					
						
						<input type=hidden name="checkSignHX" value = "Y">
						<%
					}
					
					%>
					<input type="button" id="bak_btn" class="smlbtn" onclick="window.close();" value="����"></input>&nbsp;
					
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
var ErrorMsg = "��֤����";
var SuccessMsg = " ";
function idobj(userID){
	return document.getElementById(userID);
}

function doAdd(){

    if(idobj("OpenBankName").value==null || idobj("RelatingAcct").value=="" || isNaN(idobj("RelatingAcct").value)){
			alert("��������ȷ���Ż��߿���������");
			return false;
	}
	var flag = true;
	if(flag){
		//alert(idobj("bankID").value);
		if(confirm("��ȷ��ǩԼ�˻���Ϣ��")){
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
