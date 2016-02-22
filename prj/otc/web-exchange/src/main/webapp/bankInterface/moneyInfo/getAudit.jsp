<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.Vector" %>
<%@ page import="gnnt.trade.bank.dao.BankDAO" %>
<%@ page import="gnnt.trade.bank.dao.BankDAOFactory" %>
<%
	//设置页面的强制刷新
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");

	String str=request.getParameter("filter");
	String filter=" where type!=2 and status="+str+" ";
	BankDAO dao = BankDAOFactory.getDAO();
	Vector auditeVector = dao.getCapitalInfoList(filter);
	int audite=auditeVector.size();
%>
<%=audite%>|<%=audite%>