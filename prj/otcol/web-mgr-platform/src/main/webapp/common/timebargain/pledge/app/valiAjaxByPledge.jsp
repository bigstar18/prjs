<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="java.io.PrintWriter"%>
<%@ page import="gnnt.MEBS.base.dao.DaoHelper"%>
<%@ page import="gnnt.MEBS.base.util.SysData"%>

<%
try{
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache"); 	
	response.setContentType("text/xml");
	response.setCharacterEncoding("GBK");
	/**
	 * 质押资金级联仓库2012-05-28
	 *typeOperate 【是删除还是添加】
	 *regstockId 仓单号
	 */	
	String  regstockId =  request.getParameter("billID");
	String  typeOperate =  request.getParameter("typeOperate");
	PrintWriter outPrintWriter = response.getWriter();
	outPrintWriter.print("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	outPrintWriter.print("<context>");	
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
	if(regstockId!=null&&!regstockId.equals("")){
		String sql="";
		if("1".equals(typeOperate)){
			sql = "select s.*,t.breedName as commodityName from s_regstock s inner join t_a_breed t on s.breedid=t.breedid where 1=1 and regstockId= '"+regstockId+"'";
		}else{
		    sql = "select s.*,t.breedName as commodityName,e.billfund as billFund,e.quantity as quantity from s_regstock s inner join t_a_breed t on s.breedid=t.breedid ,t_e_pledge e where s.regstockid=e.billid and '"+regstockId+"' = regstockId ";
		}
		List<Map<String,Object>> billlist=dao.queryBySQL(sql);
		if(billlist.size()==0){
			outPrintWriter.print("<billResult>0</billResult>");
		}else{
			outPrintWriter.print("<billResult>1</billResult>");
			outPrintWriter.print("<firmId>"+billlist.get(0).get("firmID").toString()+"</firmId>");
			outPrintWriter.print("<commodityName>"+billlist.get(0).get("commodityName").toString()+"</commodityName>");
			outPrintWriter.print("<weight>"+billlist.get(0).get("weight").toString()+"</weight>");
			outPrintWriter.print("<frozenWeight>"+billlist.get(0).get("frozenWeight").toString()+"</frozenWeight>");
			if("2".equals(typeOperate)){
			outPrintWriter.print("<quantity>"+billlist.get(0).get("quantity").toString()+"</quantity>");
			outPrintWriter.print("<billFund>"+billlist.get(0).get("billFund").toString()+"</billFund>");
			}
		}
	}else{ 
		outPrintWriter.print("<billResult>0</billResult>");
	}
	outPrintWriter.flush();
	outPrintWriter.print("</context>");	
	outPrintWriter.close();
}catch(Exception e){
	e.printStackTrace();
}
%>


