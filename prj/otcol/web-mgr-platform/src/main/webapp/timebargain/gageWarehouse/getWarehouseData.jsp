<%@ page language="java" pageEncoding="GBK"%><%@ page import="java.util.List" %><%@ page import="java.util.Map" %><%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManage" %>
<%@page import="gnnt.MEBS.timebargain.manage.service.GageWarehouseManager"%>
<%@page import="gnnt.MEBS.timebargain.manage.util.QueryConditions"%>
<%@page import="gnnt.MEBS.timebargain.manage.util.SysData"%>
<%String  billid = request.getParameter("billid");

GageWarehouseManager gageWarehouseManager = (GageWarehouseManager)SysData.getBean("gageWarehouseManager");

String outWarehouse = "<span class='req'>无效的仓单号！</span>";
String test = "";
try{
	QueryConditions qc = new QueryConditions("billid","=",billid); 
	List gageBillList = gageWarehouseManager.gageWarehouseList(qc);
	List warehouseList = SettleManage.getWarehouseMsg(billid);
if(warehouseList != null && warehouseList.size()>0 && (gageBillList == null || gageBillList.size()==0)){
	Map warehouseMap = (Map)warehouseList.get(0);
	outWarehouse=((String)warehouseMap.get("BILLID"))+"#"+(warehouseMap.get("BREEDID").toString())+"#"+(warehouseMap.get("weight").toString())+"#"+((String)warehouseMap.get("FIRMID")+"#"+((String)warehouseMap.get("breedname")));
	test = "have";
}else{
	test = "nohave";
}
}catch(Exception e){
	e.printStackTrace();
}
%><%="a[]"+test+"[]"+outWarehouse+"[]b"%>