<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
交易商权限
</title>
</head>
<%
	String firmID = request.getParameter("firmID");
	pageContext.setAttribute("firmID",firmID);
	System.out.println("firmID: "+firmID);
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<c:url value="/timebargain/baseinfo/firm.do?crud=create&funcflg=updateFirmPrivilege&firmID=${firmID}"/>"  application="yes">
  <frame name="HiddFrame2"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
