<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="../globalDef.jsp"%>
<%@ include file="../session.jsp"%>
<%
BankDAO dao = BankDAOFactory.getDAO();

//获取平台接入的所有银行信息 getIsOpenBanks
Vector<FirmBankFunds> banks = null;
String firmID =Tool.delNull((String)request.getParameter("firmID"));

try{
	banks = dao.getFirmBankFunds(" and f.firmID= '"+firmID+"' ");
}catch(Exception e){
	e.printStackTrace();
}
if(banks == null || banks.size() < 0){
	banks = new Vector<FirmBankFunds>();
}
response.setContentType("text/html;charset=utf-8");
response.getWriter().println("<select name='bank' id='bank' class='normal' style='width: 100px' ><option>--请选择--</option>");
for(FirmBankFunds bank : banks){
	response.getWriter().println("<option value ='"+bank.bankID+"'>" +bank.bankName+ "</option>");
}
response.getWriter().println("</select>");
%>
