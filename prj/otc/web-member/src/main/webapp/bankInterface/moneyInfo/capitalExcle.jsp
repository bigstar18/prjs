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
String fileName = new String("客户出入金查询".getBytes("GBK"), "ISO8859-1"); 
response.setContentType("application/vnd.ms-excel;charset=GBK");
response.setHeader("Content-Disposition", "attachment;filename="+fileName+Tool.fmtDate(new Date())+".csv");

String bankID = Tool.delNull(request.getParameter("bankID"));
String capitalType = Tool.delNull(request.getParameter("capitalType"));
String capitalStatus = Tool.delNull(request.getParameter("capitalStatus"));
String firmType = Tool.delNull(request.getParameter("firmType"));
String contact = Tool.delNull(request.getParameter("contact"));
String accountName = Tool.delNull(request.getParameter("accountName"));
String s_time = Tool.delNull(request.getParameter("s_time"));
String e_time = Tool.delNull(request.getParameter("e_time"));
String trader = Tool.delNull(request.getParameter("trader"));
String launcher = Tool.delNull(request.getParameter("launcher"));
String filter = " and mf.firmType='C' and (type=0 or type=1) ";
String ORGANIZATIONID = (String)session.getAttribute("ORGANIZATIONID");
if(ORGANIZATIONID==null || ORGANIZATIONID.trim().length()<=0){
	filter += " and mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' ";
	//filter += " and exists (select customerno from m_customerinfo mc where mc.memberno='"+(String)session.getAttribute("REGISTERID")+"' and fbf.firmID=mc.customerno) ";
}else{
	filter += " and mc.organizationno='"+(String)session.getAttribute("ORGANIZATIONID")+"' ";
	//filter += " and exists (select customerno from v_customerrelateorganization mc where mc.organizationno='"+(String)session.getAttribute("REGISTERID")+"' and fbf.firmID=mc.customerno) ";
}
if(bankID != null && bankID.trim().length()>0 && !bankID.trim().equalsIgnoreCase("-2")){
	filter += " and fbc.bankID='"+bankID.trim()+"' ";
}
if(bankID != null && bankID.trim().length()>0){
	filter += " and fbc.bankID='"+bankID.trim()+"' ";
}
if(capitalType != null && capitalType.trim().length()>0){
	filter += " and fbc.type="+capitalType;
}
if(launcher != null && launcher.trim().length()>0){
	filter += " and fbc.launcher="+launcher+" ";
}
System.out.println(capitalStatus);
if(capitalStatus != null && capitalStatus.trim().length()>0){
	if(capitalStatus.equals(ProcConstants.statusBankProcessing+"")){
		filter += " and fbc.status not in ("+ProcConstants.statusSuccess+","+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+") ";
	}else if(capitalStatus.equals(ProcConstants.statusSuccess+"")){
		filter += " and fbc.status="+capitalStatus;
	}else if(capitalStatus.equals(ProcConstants.statusFailure+"")){
		filter += " and fbc.status in ("+ProcConstants.statusFailure+","+ProcConstants.statusBlunt+")";
	}
}
if(firmType != null && firmType.trim().length()>0){
	filter += " and mf.firmtype='"+firmType.trim()+"' ";
}
if(contact != null && contact.trim().length()>0){
	filter += " and fbc.contact='"+contact.trim()+"'";
}
if(accountName != null && accountName.trim().length()>0){
	filter += " and fbf.accountName like '"+accountName.trim()+"%'";
}
if(s_time != null && s_time.trim().length()>0){
	//filter += " and trunc(createtime)>=to_date('"+s_time+"','yyyy-MM-dd') ";
	filter += " and fbc.createtime>=to_date('"+s_time+"','yyyy-MM-dd') ";
}
if(e_time != null && e_time.trim().length()>0){
	//filter += " and trunc(createtime)<=to_date('"+e_time+"','yyyy-MM-dd') ";
	filter += " and fbc.createtime<to_date('"+e_time+"','yyyy-MM-dd')+1 ";
}
if(trader != null && trader.trim().length()>0){
	filter += " and fbc.trader like '%"+trader+"%' ";
}
filter += " order by fbc.createtime,fbc.id";
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
CapitalInfoOuter puter = new CapitalInfoOuter(outos,"M");
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