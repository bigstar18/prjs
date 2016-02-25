<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt' %>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.finance.base.util.Utils'%>
<%@ page import='gnnt.MEBS.finance.unit.User'%> 

    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
	<META HTTP-EQUIV="expires" CONTENT="0">
	
	<%
	  User user=new User();
	  user.setUserId("admin");
	  user.setEnabled(true);
	  user.setUserName("测试管理员");
	  session.setAttribute("logonUser", user);
	  session.setAttribute("skinstyle","default");
	%>