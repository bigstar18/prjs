<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="gnnt.trade.bank.dao.*"%>
<%@ page import="gnnt.trade.bank.vo.*"%>
<%@ page import="gnnt.trade.bank.util.*"%>
<%@ page import="gnnt.trade.bank.fileouter.*"%>
<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
%>
<%

String fileName = new String("银行流水不平记录".getBytes("GBK"), "ISO8859-1"); 
response.setContentType("application/vnd.ms-excel;charset=GBK");
response.setHeader("Content-Disposition", "attachment;filename="+fileName+Tool.fmtDate(new Date())+".csv");
String bankID = Tool.delNull(request.getParameter("bankID"));
java.util.Date tradeDate = Tool.strToDate(request.getParameter("s_time"));
String thispage = request.getParameter("thispage");
int[] pageinfo = null;
if("1".equals(thispage)){
	pageinfo = new int[4];
	pageinfo[2] = Integer.parseInt(request.getParameter("pageSize"));
	pageinfo[1] = Integer.parseInt(request.getParameter("pageIndex"));
}
out.clear();
out = pageContext.pushBody();
OutputStream outos=null;
try{
outos=response.getOutputStream();
MarketNoCapitalOuter puter = new MarketNoCapitalOuter(outos);
puter.output(bankID,tradeDate,pageinfo);
}catch(Exception e){
	e.printStackTrace();
}finally{
	try{
		outos.flush();
	}catch(Exception e){
	}
	try{
		outos.close();
	}catch(Exception e){
	}
}
%>