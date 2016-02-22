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

String fileName = new String("交易账号信息".getBytes("GBK"), "ISO8859-1"); 
response.setContentType("application/vnd.ms-excel;charset=GBK");
response.setHeader("Content-Disposition", "attachment;filename="+fileName+Tool.fmtDate(new Date())+".csv");

String filter = " ";
String bankID = request.getParameter("bankID");
String isOpen = request.getParameter("isOpen");
String status = request.getParameter("status");
String firmType = request.getParameter("firmType");
String cardType = request.getParameter("cardType");
String frozenFuns = request.getParameter("frozenFuns");
String belevemember = request.getParameter("belevemember");
String contact = request.getParameter("contact");
String account = request.getParameter("account");
String accountName = request.getParameter("accountName");
if(bankID != null && bankID.length()>0){
	filter += " and fbf.bankID='"+bankID+"' ";
}
if(isOpen != null && isOpen.length()>0){
	filter += " and fbf.isOpen="+isOpen+" ";
}
if(status != null && status.length()>0){
	filter += " and fbf.status="+status+" ";
}
if(firmType != null && firmType.length()>0){
	filter += " and mf.firmType='"+firmType+"' ";
}
if(cardType != null && cardType.length()>0){
	if("other".equals(cardType)){
		filter += " and fbf.cardType not in ('1','8','9') ";
	}else{
		filter += " and fbf.cardType='"+cardType+"' ";
	}
}
if(frozenFuns != null && frozenFuns.length()>0){
	if("1".equals(frozenFuns)){
		filter += " and fbf.frozenFuns<>0";
	}else{
		filter += " and fbf.frozenFuns=0";
	}
}
if(belevemember != null && belevemember.trim().length()>0){
	if("M".equalsIgnoreCase(firmType) || "S".equalsIgnoreCase(firmType)){
		filter += " and fbf.firmID='"+belevemember.trim()+"' ";
	}else{
		filter += " and mc.memberno='"+belevemember.trim()+"' ";
	}
}
if(contact != null && contact.trim().length()>0){
	filter += " and fbf.contact='"+contact.trim()+"' ";
}
if(account != null && account.trim().length()>0){
	filter += " and fbf.account='"+account.trim()+"' ";
}
if(accountName != null && accountName.trim().length()>0){
	filter += " and fbf.accountName like '"+accountName.trim()+"%' ";
}
filter += " order by fbf.firmID,fbf.bankid ";
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
FirmAccountOuter puter = new FirmAccountOuter(outos);
puter.output(filter,pageinfo);
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