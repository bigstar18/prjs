<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<base target="_self"> 
<%
request.setCharacterEncoding("GBK");
boolean isChartoGBK=false;//�Ƿ��������������Ҫת��
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

String returnMsg="����";
long result=-1;

if(cp==null){
	returnMsg="����ʧ�ܣ����Ӵ�����ʧ�ܣ�";
}else{
	if((""+ProcConstants.outMoney2AccountType).equals(inoutMoney)) {
		OutMoneyMarket omm = new OutMoneyMarket();
		omm.bankID = CITICBankID;
		omm.firmID = (String)session.getAttribute("REGISTERID");
		omm.trader = (String)session.getAttribute("REGISTERID")+"_trader";
		omm.account = account;
		omm.contact = contact;
		omm.money = Double.parseDouble(money);
		omm.remark = "�г�������"+account;
		ReturnValue rv = cp.outMoney2Account(omm,InOutStart,account,accountName,bankName,OpenBankCode);
		result = rv.result;
		returnMsg = rv.remark;
	}else{
		returnMsg="����ʧ�ܣ�������������";
	}
	if(result==0){
		returnMsg="���ֳɹ����뼰ʱͨ���տ��˻���ѯ���������";
	}else if(result==5){
		returnMsg="��������ɹ����뼰ʱͨ��������ˮ��ѯ�鿴��������״̬����ˮ�ɹ�֮��ͨ���տ��˻���ѯ���������";
	}else{
		if(returnMsg==null||returnMsg.trim().length()<=0){
			returnMsg = ErrorCode.error.get(result);
			if(returnMsg == null || returnMsg.trim().length()<=0){
				returnMsg = "���з��ش����룺"+result;
			}
		}
	}
}
%>
<SCRIPT LANGUAGE="JavaScript">
alert("<%=returnMsg%>");
window.close();
</SCRIPT>