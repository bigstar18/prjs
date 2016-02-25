<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<%
	String CommodityID = request.getParameter("CommodityID");
	String FirmID_B = request.getParameter("FirmID_B");
	String FirmID_S = request.getParameter("FirmID_S");
	//int Result = Integer.parseInt(request.getParameter("Result"));
	long Quantity = Long.parseLong(request.getParameter("Quantity"));
		
 	long checkResult=SettleManage.checkInsertSettle(CommodityID,FirmID_B,FirmID_S,Quantity);   
%>
<%=checkResult %>
