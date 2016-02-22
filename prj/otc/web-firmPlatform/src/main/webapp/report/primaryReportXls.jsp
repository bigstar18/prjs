<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@page import="gnnt.MEBS.report.assemble.CustomerExcelPlay"%>
<%@page import="gnnt.MEBS.report.model.CompleteData"%>

<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");

String fileName = new String("客户报表".getBytes("GBK"), "ISO8859-1"); 
response.setContentType("application/vnd.ms-excel;charset=GBK");
response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");
%>

<%
List<CompleteData> completeDataList=(List<CompleteData>)request.getAttribute("completeDataList");
CompleteData com=completeDataList.get(0);
OutputStream out1=null;
try{
	out1=response.getOutputStream();
	CustomerExcelPlay e=new CustomerExcelPlay();
	e.getExcel("客户报表",com,out1,request);
}catch(Exception e){
	e.printStackTrace();
}
finally{
	try{
		out1.flush();
	}catch(Exception e){
	}
	try{
		out1.close();
	}catch(Exception e){
	}

}

%>