<%@ page contentType="text/html;charset=GBK"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.Connection"%>
<%@page import="gnnt.MEBS.timebargain.hqweb.dao.QTAcquisitionDao"%>
<%@page import="gnnt.MEBS.timebargain.hqweb.dao.jdbc.QTAcquisitionDaoJdbc"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.util.Date"%>
<%@page import="gnnt.MEBS.timebargain.hqweb.dao.jdbc.QTAcquisitionDaoJdbc"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%
	Context ctx = new InitialContext();
	DataSource ds = (DataSource)ctx.lookup("java:comp/env/hqForGnnt");
	Connection conn = ds.getConnection();
	QTAcquisitionDao qtDao= new QTAcquisitionDaoJdbc(conn);
	String methodName=request.getParameter("methodName");
	String params=request.getParameter("params");
	if(methodName.equals("loadCommodity")){
		out.print(qtDao.loadCommodity());
	}else if(methodName.equals("loadTradeTime")){
		out.print(qtDao.loadTradeTime());
	}else if(methodName.equals("getCommodityTradeSec")){
		out.print(qtDao.getCommodityTradeSec());
	}
	
	if(conn !=null){
		conn.close();
		conn = null;
	}
%>