<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.manager.FirmManager' %>
<%@ page import='gnnt.MEBS.member.firm.unit.Trader' %>
<%
	String traderId = request.getParameter("traderId");
	String sign = null;
	if(traderId != null){
		Trader trader = FirmManager.getTraderById(traderId);
		if(trader!=null)
		{
		  sign="false";
		}
		else 
		{
		   sign="true";
		}
	}
%> 
<%=sign%> 