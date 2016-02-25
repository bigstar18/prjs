<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.AccountManager' %>
<%@ page import='gnnt.MEBS.finance.unit.Account' %>
<%
    	String name = null;
	String code = request.getParameter("code");
	if(code != null){
		Account account = AccountManager.getLeafAccountByCode(code);
		name = account.getName();
	}
%>
<%=name%> 