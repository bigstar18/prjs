<%@ page language="java" pageEncoding="GBK"%><%@ page import="java.util.List" %><%@ page import="java.util.Map" %><%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<%@page import="gnnt.MEBS.timebargain.manage.service.GageWarehouseManager"%>
<%@page import="gnnt.MEBS.timebargain.manage.util.QueryConditions"%>
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%>
<%
String  firmid = request.getParameter("firmid");
GageWarehouseManager gageWarehouseManager = (GageWarehouseManager)SysData.getBean("gageWarehouseManager");

String test = "";
try{
	QueryConditions qc = new QueryConditions("firmid","=",firmid); 
	List firmList = gageWarehouseManager.validateFirmList(qc);

    if(firmList != null && firmList.size()>0 ){
	  test = "have";
    }else{
	  test = "nohave";
    }
}catch(Exception e){
	e.printStackTrace();
}
%>
<%="a[]"+test+"[]b"%>
