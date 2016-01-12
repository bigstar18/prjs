<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>

<%
	String title = request.getParameter("title");
	String reportName = null;

 	SimpleDateFormat tempDate = new SimpleDateFormat("yyyy_MM_dd-HH_mm");
	String datetime = tempDate.format(new java.util.Date());	 
	response.reset();
	response.setContentType("application/vnd.ms-excel;charset=GBK");
	response.setHeader("Content-disposition","attachment; filename="+title+datetime +".xls");
%>
 