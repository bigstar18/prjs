<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManagePattern" %>
<%
	String commodityid = request.getParameter("commodityid");
	String FirmID_S = request.getParameter("FirmID_S");
	
    List regstockList = SettleManagePattern.getRegStocks(commodityid, FirmID_S);
     
    String regstockStr = "";
    if(regstockList != null && regstockList.size()>0){
    	for(int regs = 0 ; regs < regstockList.size() ; regs ++){
    		Map regsMap = (Map)regstockList.get(regs);
    		String regstock = (String)regsMap.get("regstockid");
    		regstockStr = regstockStr + regstock + ";";
    	}
    }
%>
<%=regstockStr %>