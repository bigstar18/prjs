<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%@ page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%>
<%@ page import = "com.hitrust.trustpay.client.market.IMarketTagName" %>
<%@ page import = "com.hitrust.trustpay.client.IFunctionID" %>

<base target="_self"> 

<%
	//ABCCapitalProcessorRMI cp = null;
	//try{
	//	cp = getABCBankUrl("050");
	//					
	//}catch(Exception e){
	//	e.printStackTrace();
	//}
	String[] remarks = request.getParameterValues("ck");
	String pwd = request.getParameter("pwd");
	String bankID = "";
	String id = "";
	if(remarks != null)
	{
		
		String str="";
		for(int i=0;i<remarks.length;i++)
		{
			String[] remark = remarks[i].split(",");
			id = remark[9];
			bankID = remark[0];
		}
		
	}
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	
	CorrespondValue corr = new CorrespondValue();
	FirmValue firmValue = dao.getFirm(firmID);
	corr.firmID = firmValue.firmID;
	corr.cardType = firmValue.cardType;
	corr.card = firmValue.card;
	corr.contact = firmValue.contact;
	corr.name = firmValue.firmName;
	String bankIdAndUrls[][]={
	{"050","../ext_connector/ext_abc_005/openAccount.jsp"},
	{"006","../ext_connector/ext_hxb_006/firmUserList.jsp"},
	{"021","../ext_connector/ext_ceb_021/childopenAccountmedo.jsp"},
	{"66","../ext_connector/ext_gfb_66/openAccount.jsp"},
	{"016","../ext_connector/ext_citic_016/openAccount.jsp"},
	{"031","../ext_connector/ext_up_031/openAccount.jsp"},
	{"011","../ext_connector/ext_icbce_011/openAccount.jsp"},
	{"027","../ext_connector/ext_tlapay_027/openAccount.jsp"},
	{"028","../ext_connector/ext_tlgpay_028/openAccount.jsp"}
	};
	String sql=" and bankid in (";
	String BankUrl="";
	for(int i=0;i<bankIdAndUrls.length;i++){
		sql+="'"+bankIdAndUrls[i][0]+"',";
		BankUrl+=bankIdAndUrls[i][0]+","+bankIdAndUrls[i][1]+";";
	}
		sql=sql.substring(0,sql.length()-1);
		sql+=")";
		
	
	
%>
<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">
	<link rel="stylesheet" href="../skin/default/css/style.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/print.css" type="text/css"/>
	<link rel="stylesheet" href="../css/report.css" type="text/css"/>
	<script language="javascript" src="../tools.js"></script>
    <title>����ǩԼ��Ϣ</title>
  </head>
  
  <body style="overflow-y: hidden">
  <object classid=clsid:62B938C4-4190-4F37-8CF0-A92B0A91CC77 
		codebase="NetSign.cab" data=data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA== 
		id=InfoSecNetSign1 style="HEIGHT: 0px; WIDTH: 0px" VIEWASTEXT width="0" height="0">
    <embed width="0" height="0" src="data:application/x-oleobject;base64,xDi5YpBBN0+M8KkrCpHMdwADAACJEwAAPAcAAA==" type="application/x-oleobject">
    </embed> 
</object>
  	<form id="frm" action="02_openAccount2.jsp" method="post" name="frm">
	<div style="overflow:auto;height:355;">
	 <table border="0" width="95%" align="center">
		<tr>
		  <td>
			<div class="st_title">&nbsp;����ǩԼ��Ϣ</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35" class="st_bor">
				<tr>
					<td align="right" width="110">&nbsp;</td>
					<td align="left" width="100">
						<input type="hidden" name="firmID" value="<%=firmID%>" id="firmID" class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="contactFalg">
					<td align="right"><%=CONTACTTITLE%>��&nbsp;</td>
					<td align="left">
						<input name="contact" id="contact" disabled='disabled' readonly value="<%=Tool.delNull(corr.contact)%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
					<td align="left">
						<input name="firmcontact" type="hidden" readonly value="<%=Tool.delNull(corr.contact)%>"  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="frimNameFlag">
					<td align="right">���ƣ�&nbsp;&nbsp;</td>
					<td align="left">
						<input name="CustName" disabled='disabled' readonly value="<%=Tool.delNull(corr.name)%>" type=text  class="input_text" maxlength="10" style="width: 140px">
					</td>
				</tr>
				<tr height="35">
					<td align="right">ǩԼ���У�&nbsp;</td>
					<td align="left">
						<%
						Vector bankList = dao.getBankList(sql);
						%>
						<select onblur="myblur('bankID');" id="bankID" name="bankID" class="normal" style="width:140px" onchange="changeBank()">
								 <option value="">��ѡ������</option> 
							<%
							for(int i=0;i<bankList.size();i++){
								BankValue bank = (BankValue)bankList.get(i);
								%>
								<option value="<%=bank.bankID%>" ><%=bank.bankName%></option>
								<%
							}
							%>
						</select>
					</td>
					<td><div id="bankIDTip" class=""></div></td>
					<td><font color='#FF0000'>(�����г�������֧�ִ˹���)</font></td>
				</tr>
				<tr height="35" id="accountFlag" style="display:none;">
					<td align="right">���п��ţ�&nbsp;</td>
					<td align="left">
						<input type="text" name="account" id="account" class="input_text"  maxlength="30" style="width: 140px">
					</td>
				</tr>
				<tr height="35" id="cardTypeFlag">
					<td align="right">֤�����ͣ�&nbsp;</td>
					<td align="left">
						<select id="cardType" name="cardType" disabled="disabled"  style="width: 140px">
							<option value="">����֤��</option>
							<option value="1" <%=(corr.cardType!=null && corr.cardType.equals("1")) ? "selected" : ""%>>���֤</option>
							<option value="8" <%=(corr.cardType!=null && corr.cardType.equals("8")) ? "selected" : ""%>>��֯��������</option>
							<option value="9" <%=(corr.cardType!=null && corr.cardType.equals("9")) ? "selected" : ""%>>Ӫҵִ��</option>
							<option value="a" <%=(corr.cardType!=null && corr.cardType.equals("a")) ? "selected" : ""%>>���˴���֤</option>
						</select>
					</td>
				</tr>
				<tr height="35" id="cardFlag">
					<td align="right">֤�����룺&nbsp;</td>
					<td align="left">
						<input id="card" name="card" readonly disabled="disabled" value="<%=Tool.delNull(corr.card)%>" type=text  class="text" maxlength="18"  style="width: 140px">
						<input type="hidden" value="rgst" name="falg" >
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
					<input type="button" id="sub_btn" class="smlbtn" onclick="doAdd();" value="��һ��"></input>&nbsp;
					<input type="button" id="bak_btn" class="smlbtn" onclick="window.close();" value="����"></input>&nbsp;
					<input type="hidden" id="submitFlag" name="submitFlag" value="">
				</td>
			</tr>
		</table>
	</form>
	<a href="#" id="openAcc" ></a>	
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
	var flag = true;
	flag = myblur("bankID");
	var bankid = frm.bankID.value;
	if(bankid == '012'){
		var contact = '<%=corr.contact%>';
		var id = '<%=id%>';
		flag = false;
		//frm.action = "./ext_boc_018/openAccount.jsp";
		window.showModalDialog("./ext_boc_012/inputPwd.jsp?&contact="+contact+"&id="+id+"&flag=0","","dialogWidth=360px; dialogHeight=10px; status=no;scroll=no;help=no;");
		window.close();
	}
	if(bankid == '050' || bankid == '060'){//�����ũ�У��������п���
		if(idobj("account").value==null || idobj("account").value=="" || isNaN(idobj("account").value)){
			alert("��������ȷ����");
			return false;
		}
	}
	if(flag){
		frm.submit();
	}			
}


function tipclass(userID,flag){
	var tip = idobj(userID+"Tip");
	if(flag){
		tip.className=CorrectClass;
	}else{
		tip.className=ErrorClass;
	}
}

function changeBank(){
	var bankid = frm.bankID.value;
	if(bankid == '050'||bankid == '060'){//�����ũ����ʾ���п�������
		document.getElementById("accountFlag").style.display = "block";
		frm.action = "02_openAccount2.jsp";
	}else if(bankid == '006'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_hxb_006/firmUserList.jsp";
	}else if(bankid == '66'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_gfb_66/openAccount.jsp";
	}else if(bankid == '021'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_ceb_021/firmUserList.jsp";
	}else if(bankid == '016'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_citic_016/openAccount.jsp";
	}else if(bankid == '011'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_icbce_011/openAccount.jsp";
	}else if(bankid == '031'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_up_031/openAccount.jsp";
	}else if(bankid == '027'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_tlapay_027/openAccount.jsp";
	}else if(bankid == '028'){
		document.getElementById("accountFlag").style.display = "none";
		frm.action = "../ext_connector/ext_tlgpay_028/openAccount.jsp";
	}else{
		document.getElementById("accountFlag").style.display = "none";
	}
}

function myblur(strBankID){
	var bankID = idobj(strBankID);
	var tip = idobj(strBankID+"Tip");
	var flag = true;
	if(bankID.value==""){
		flag = false;
		tip.innerHTML = "��ѡ������";
	}else{
		tip.innerHTML = SuccessMsg;
	}
	tipclass(strBankID,flag);
	return flag;
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
