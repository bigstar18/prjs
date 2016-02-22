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
String fileName = new String("客户管理".getBytes("GBK"), "ISO8859-1"); 
response.setContentType("application/vnd.ms-excel;charset=GBK");
response.setHeader("Content-Disposition", "attachment;filename="+fileName+Tool.fmtDate(new Date())+".csv");

String filter = " and mf.firmType='C' ";
String ORGANIZATIONID = (String)session.getAttribute("ORGANIZATIONID");
if(ORGANIZATIONID==null || ORGANIZATIONID.trim().length()<=0){
	filter += " and mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' ";
	//filter += " and exists (select customerno from m_customerinfo mc where mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' and fbf.firmID=mc.customerno) ";
}else{
	filter += " and mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' ";
	//filter += " and exists (select customerno from v_customerrelateorganization mc where mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' and fbf.firmID=mc.customerno) ";
}
String bankID = request.getParameter("bankID");
String isOpen = request.getParameter("isOpen");
String status = request.getParameter("status");
String cardType = request.getParameter("cardType");
String frozenFuns = request.getParameter("frozenFuns");
String firmID = request.getParameter("firmID");
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
ExcelPlay ep = new ExcelPlay();
OutputStream outos=null;
try{
outos=response.getOutputStream();
FirmAccountOuter puter = new FirmAccountOuter(outos,"M");
puter.output(filter,pageinfo);
}catch(Exception e){
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