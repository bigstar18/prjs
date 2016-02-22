<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=true;//是否存在乱码问题需要转码
String UPBankID = "031";
UPBankDAO dao = BankDAOFactory.getUPDAO();
UPCapitalProcessorRMI cp=getUPBankUrl(UPBankID);
	try {		
		cp = getUPBankUrl(UPBankID);
	} catch(Exception e) {
		e.printStackTrace();
	}
String firmID = (String)session.getAttribute("REGISTERID");
String contact = request.getParameter("contact");
String account1 = request.getParameter("account1");
String money = request.getParameter("money");
String bankName = Tool.delNull(request.getParameter("BankName"));
String OpenBankCode = request.getParameter("OpenBankCode");
String accountName = request.getParameter("accountName");
String account = request.getParameter("account");
String inoutMoney = request.getParameter("inoutMoney");
String bankProvince = request.getParameter("bankProvince");
String bankCity = request.getParameter("bankCity");
String accountType = request.getParameter("accountType");
if(isChartoGBK){
	bankName=new String(bankName.getBytes("ISO-8859-1"),"gbk");
	accountName=new String(accountName.getBytes("ISO-8859-1"),"gbk");
}

String returnMsg="";
long result=-1;

if(cp==null){
	returnMsg="连接处理器失败！";
}else{
System.out.println("代收付"+inoutMoney);
	if(("1").equals(inoutMoney)) {
		OutMoneyMarket omm = new OutMoneyMarket();
		omm.bankID = UPBankID;
		omm.firmID = (String)session.getAttribute("REGISTERID");
		omm.trader = (String)session.getAttribute("REGISTERID")+"_trader";
		omm.account = account;
		omm.contact = contact;
		omm.money = Double.parseDouble(money);
		omm.remark = "市场端出金"+account;
		ReturnValue rv = cp.outMoneyMarket(omm,account,OpenBankCode,bankName,bankProvince,bankCity,accountType);
		result = rv.result;
		returnMsg = rv.remark;
	}else if(("0").equals(inoutMoney)) {
		InMoneyMarket imm = new InMoneyMarket();
		imm.firmID = (String)session.getAttribute("REGISTERID");
		imm.trader = (String)session.getAttribute("REGISTERID")+ "_trader";
		imm.bankID = UPBankID;
		imm.account = account;
		imm.contact = contact;
		imm.money = Double.parseDouble(money);
		
		imm.remark = "市场端发起入金";
		ReturnValue rv = cp.inMoneyMarket(imm,account,OpenBankCode,bankName,bankProvince,bankCity,accountType);
		result = rv.result;
		returnMsg = rv.remark;
	}
	if(result==0){
		returnMsg="转账成功，请及时通过收款账户查询到账情况！";
	}else if(result==5){
		returnMsg="转账成功，请及时通过流水查询查看交易最新状态，流水成功之后通过账户查询到账情况！";
	}else{
		if(returnMsg==null||returnMsg.trim().length()<=0){
			returnMsg = ErrorCode.error.get(result);
			if(returnMsg == null || returnMsg.trim().length()<=0){
				returnMsg = "银行返回错误码："+result;
			}
		}
	}
}
%>
<SCRIPT LANGUAGE="JavaScript">
alert("<%=returnMsg%>");
window.close();
</SCRIPT>