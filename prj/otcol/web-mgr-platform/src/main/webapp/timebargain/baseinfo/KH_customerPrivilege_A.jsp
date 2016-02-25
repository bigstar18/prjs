<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
二级客户代码权限
</title>
</head>
<%
	String customerID = request.getParameter("customerID");
	pageContext.setAttribute("customerID",customerID);
	System.out.println("customerID: "+customerID);
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<c:url value="/timebargain/baseinfo/customer.do?crud=create&funcflg=updateCustomerPrivilege&customerID=${customerID}"/>"  application="yes">
  <frame name="HiddFrame2"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
