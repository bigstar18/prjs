<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManagePattern" %>
<%
	String CommodityID = request.getParameter("CommodityID");
	String FirmID_B = request.getParameter("FirmID_B");
	String FirmID_S = request.getParameter("FirmID_S");
	int result = Integer.parseInt(request.getParameter("Result"));
	long Quantity = Long.parseLong(request.getParameter("Quantity"));
	String regsto = request.getParameter("regsto");	
 	double checkResult=SettleManagePattern.checkInsertSettle(CommodityID,FirmID_B,FirmID_S,regsto,Quantity,result);   
%>
<%=checkResult %>