<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="gnnt.MEBS.common.security.util.Configuration"%>
<center class="reportHeadForMarketName">
	<%
	  String marketName="";
	  try{
	     marketName=new Configuration().getSection("MEBS.marketInfo").getProperty("marketName");
	  }
	  catch(Exception e)
	  {
	     
	  }
	%>
	<%=marketName%>
	</center>