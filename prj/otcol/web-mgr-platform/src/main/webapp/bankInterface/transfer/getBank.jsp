<%@page contentType="text/plain;charset=GBK" %><%@page import="java.util.*"%><%@page import="java.sql.*"%><%@page import="gnnt.trade.bank.dao.*"%><%@page import="gnnt.trade.bank.vo.*"%><%@page import="gnnt.trade.bank.util.*"%><%@page import="java.rmi.*"%><%@page import="gnnt.trade.bank.processorrmi.CapitalProcessorRMI"%><%
 		BankDAO dao = BankDAOFactory.getDAO();
		String s = request.getParameter("tradeType");
	    Vector<BankValue> bankList = dao.getTransferBanks(" and type=" + s );
		StringBuffer sb = new StringBuffer();
		String str = String.valueOf(bankList.size());
		for(int i=0;i<4-str.length();i++){
		   str=str+" ";
		}
		sb.append(str);
		BankValue bank = null;
        for(int i=0;i<bankList.size();i++){
		    bank = bankList.get(i);
			sb.append(bank.bankID).append(":").append(bank.bankName);
			if(i!=(bankList.size()-1)){
				sb.append(",");
			}
		}
%><%=sb.toString()%>