<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<base target="_self">

<%
	BankDAO dao = BankDAOFactory.getDAO();
	String firmID = request.getParameter("firmID");
	String bankID = request.getParameter("bankID");
	String contact = request.getParameter("contact");
	
	
	CorrespondValue corr = new CorrespondValue();
	FirmValue firmValue = dao.getFirm(firmID);
	corr.firmID = firmValue.firmID;
	corr.cardType = firmValue.cardType;
	corr.card = firmValue.card;
	corr.contact = firmValue.contact;
	corr.name = firmValue.firmName;
	String bankIdAndUrls[][]={
	{"006","../ext_connector/ext_hxb_006/delAccounthx.jsp"},
	{"005","../ext_connector/ext_abc_005/delAccount.jsp"},
	{"050","../ext_connector/ext_abc_005/delAccount.jsp"}
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
	<script language="javascript" src="../lib/tools.js"></script>
    <title>发起解约信息</title>
  </head>
  
<body>
<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
			    		
				
                <input type="hidden" value="<%=request.getParameter("bankID")%>" name="bankID">
				<input type="hidden" value="<%=request.getParameter("firmID")%>" name="firmID" >	
				<input type="hidden" value="<%=request.getParameter("contact")%>" name="contact">
				
				
		</table>
<a href="#" id="delAcc"></a>
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


var bankUrlForOneBank=null;
var BankIdAndUrl="<%=BankUrl%>".split(';');
for(var i=0;i<BankIdAndUrl.length;i++){
	var bankUrls=BankIdAndUrl[i].split(',');
	if(bankUrlForOneBank!=null){
		break;
	}else{
		for(var j=0;j<bankUrls.length;j++){
			if('<%=bankID %>'==bankUrls[0]){
				bankUrlForOneBank=bankUrls[1];
			break;
			}
		}
	}
}
if(bankUrlForOneBank!=null){
    
	document.getElementById("delAcc").href="03_delAccount2.jsp?bankID="+idobj("bankID").value+"&firmID="+idobj("firmID").value+"&contact="+idobj("contact").value;	
	document.getElementById("delAcc").click();
}else{
	window.close();
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
function myblur(strBankID){
	var bankID = idobj(strBankID);
	var tip = idobj(strBankID+"Tip");
	var flag = true;
	if(bankID.value==""){
		flag = false;
		tip.innerHTML = "请选择银行";
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
