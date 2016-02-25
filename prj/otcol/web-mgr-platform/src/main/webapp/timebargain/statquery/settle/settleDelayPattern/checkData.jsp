<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelayPattern" %>
<%try{
	String CommodityID = request.getParameter("CommodityID");
	String FirmID_B = request.getParameter("FirmID_B");
	String FirmID_S = request.getParameter("FirmID_S");
	String settleDate=request.getParameter("settleDate");
	int result = Integer.parseInt(request.getParameter("Result"));
	long Quantity = Long.parseLong(request.getParameter("Quantity"));
	String regsto = request.getParameter("regsto");		
 	double checkResult=SettleManageDelayPattern.checkInsertSettle(CommodityID,settleDate,FirmID_B,FirmID_S,regsto,Quantity,result);   
%>
<%=checkResult %>
<%
	}catch(Exception e){
		e.printStackTrace();	
	}
%>