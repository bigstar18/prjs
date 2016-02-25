<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
二级代码权限
</title>
</head>
<%
	String id = request.getParameter("id");
	pageContext.setAttribute("id",id);
	System.out.println("id: "+id);
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<c:url value="/timebargain/baseinfo/customer.do?crud=update&funcflg=updateCustomerPrivilege&id=${id}"/>"  application="yes">
  <frame name="HiddFrame2"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
