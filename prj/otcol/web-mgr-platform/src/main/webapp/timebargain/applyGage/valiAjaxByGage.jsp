<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.io.PrintWriter"%>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.CommodityManager"%>
<%@page import="gnnt.MEBS.timebargain.manage.model.Commodity"%>
<%@page import="gnnt.MEBS.timebargain.manage.webapp.action.BaseAction"%>

<%
try{
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache"); 	
	response.setContentType("text/xml");
	response.setCharacterEncoding("GBK");
	String  commodityId =  request.getParameter("commodityId");
	String  customerId =  request.getParameter("customerId");
	PrintWriter outPrintWriter = response.getWriter();
	outPrintWriter.print("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	outPrintWriter.print("<context>");	
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	if(commodityId!=null&&!commodityId.equals("")){
		CommodityManager mgr = (CommodityManager) new  BaseAction().getBean("commodityManager");
		Commodity com= mgr.getCommodityById(commodityId); 
		
		if(com.getCommodityID()==null){ 
			outPrintWriter.print("<commodityResult>0</commodityResult>");
		}else{  
			  if(com.getAheadSettlePriceType()==0)  
				  outPrintWriter.print("<aheadSettlePriceType>0</aheadSettlePriceType>");     
			  else outPrintWriter.print("<aheadSettlePriceType>1</aheadSettlePriceType>"); 
			outPrintWriter.print("<commodityResult>1</commodityResult>");
		}
	}else{ 
		outPrintWriter.print("<commodityResult>0</commodityResult>");
	}
	if(customerId!=null&&!customerId.equals("")){
		String sql = "select * from T_Customer where '"+customerId+"' = customerid ";
		List customerlist=dao.queryBySQL(sql); 
		if(customerlist.size()==0){
			outPrintWriter.print("<customerResult>0</customerResult>");
		}else{
			outPrintWriter.print("<customerResult>1</customerResult>");
		}
	}else{
		outPrintWriter.print("<customerResult>0</customerResult>");
	}
	outPrintWriter.flush();
	outPrintWriter.print("</context>");	
	outPrintWriter.close();
}catch(Exception e){
	e.printStackTrace();
}
%>


