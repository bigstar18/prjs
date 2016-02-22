<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//是否存在乱码问题需要转码
String CITICBankID = "016";
CITICCapitalProcessorRMI cp=getCITICBankUrl(CITICBankID);
String firmID = (String)session.getAttribute("REGISTERID");
String contact = request.getParameter("contact");
String account1 = request.getParameter("account1");
String money = request.getParameter("money");
String InOutStart = request.getParameter("InOutStart");
String bankName = Tool.delNull(request.getParameter("BankName"));
String OpenBankCode = request.getParameter("OpenBankCode");
String accountName = request.getParameter("accountName");
String account = request.getParameter("account");
String inoutMoney = request.getParameter("inoutMoney");
if(isChartoGBK){
	bankName=new String(bankName.getBytes("ISO-8859-1"),"gbk");
	accountName=new String(accountName.getBytes("ISO-8859-1"),"gbk");
}

String returnMsg="提现";
long result=-1;

if(cp==null){
	returnMsg="提现失败，连接处理器失败！";
}else{
	if((""+ProcConstants.outMoney2AccountType).equals(inoutMoney)) {
		OutMoneyMarket omm = new OutMoneyMarket();
		omm.bankID = CITICBankID;
		omm.firmID = (String)session.getAttribute("REGISTERID");
		omm.trader = (String)session.getAttribute("REGISTERID")+"_trader";
		omm.account = account;
		omm.contact = contact;
		omm.money = Double.parseDouble(money);
		omm.remark = "市场端提现"+account;
		ReturnValue rv = cp.outMoney2Account(omm,InOutStart,account,accountName,bankName,OpenBankCode);
		result = rv.result;
		returnMsg = rv.remark;
	}else{
		returnMsg="提现失败，提现类型有误！";
	}
	if(result==0){
		returnMsg="提现成功，请及时通过收款账户查询到账情况！";
	}else if(result==5){
		returnMsg="提现申请成功，请及时通过提现流水查询查看交易最新状态，流水成功之后通过收款账户查询到账情况！";
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