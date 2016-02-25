<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
交易员权限
</title>
</head>
<%
	String traderID = request.getParameter("traderID");
	pageContext.setAttribute("traderID",traderID);
	System.out.println("traderID: "+traderID);
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<c:url value="/timebargain/baseinfo/trader.do?crud=create&funcflg=updateTraderPrivilege&traderID=${traderID}"/>"  application="yes">
  <frame name="HiddFrame2"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
