<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/session.jsp"%>
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String username = (String)session.getAttribute("FIRMID");
	if(username==null||"".equals(username)){	
		//request.getRequestDispatcher("index.jsp").forward(request,response);
	}
%>

<%
long lid = -1000;
try{
	lid = ((Long)session.getAttribute("LOGINID")).longValue();	
}catch(Exception se){
	
}
%>