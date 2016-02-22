<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ page import="gnnt.trade.bank.dao.tlapay.*"%>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%
	TLAPayBankDAO dao = BankDAOFactory.getTLAPayDAO();
	String OpenBankCodeText = Tool.delNull(request.getParameter("OpenBankCodeText"));
	
	Vector<BankCodes> bankCodes = dao.getBankCode(OpenBankCodeText);
	BankCodes bankCode = new BankCodes();
	
	response.getWriter().print("<select name='OpenBankCode' id='OpenBankCode' style='width: 140px'>");
	for(int i=0;i<bankCodes.size();i++){
		bankCode = bankCodes.get(i);
		response.getWriter().print("<option value='" + bankCode.bankCode + "'>" + bankCode.bankName + "</option>");
	}
	response.getWriter().print("</select>");
	
%>
