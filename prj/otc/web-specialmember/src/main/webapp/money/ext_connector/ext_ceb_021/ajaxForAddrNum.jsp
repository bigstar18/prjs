<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="java.util.Date"/>
<%@ include file="../../globalDef.jsp"%>
<%@ include file="../../session.jsp"%>
<%
	CEBBankDao dao = BankDAOFactory.getCEBDAO();
	//String bankName = new String(Tool.delNull(request.getParameter("bankName")).getBytes("ISO-8859-1"),"GBK");
	String bankName = Tool.delNull(request.getParameter("bankName"));
	if("".equals(bankName)){
		response.getWriter().print("<span>数据有问题</span>");
	}else{
		String filterForBanks = "where nbkname like '" + bankName + "'";
		Vector<BanksInfoValue> result = dao.getBanksInfo(filterForBanks);
		BanksInfoValue bankValue = result.get(0);
		response.getWriter().print("<table border='0'><tr height='35'>");
		response.getWriter().print("<td align='right'>开户行地址：</td>");
		response.getWriter().print("<td align='left'>");
		response.getWriter().print("<input value='" + bankValue.nbkaddrss + "' name='RelatingAcctBankAddr' id='RelatingAcctBankAddr' readonly type=text  maxlength='30' style='width: 300px'>");
		response.getWriter().print("<span>*</span></td>");
		response.getWriter().print("</tr>");
		response.getWriter().print("<tr height='35'>");
		response.getWriter().print("<td align='right'>开户行行号：</td>");
		response.getWriter().print("<td align='left'>");
		response.getWriter().print("<input value='" + bankValue.nbkcode + "' name='RelatingAcctBankCode' id='RelatingAcctBankCode' readonly type=text  maxlength='12' style='width: 300px'>");
		response.getWriter().print("<span>*</span></td>");
		response.getWriter().print("</tr></table>");
	}
	
%>
