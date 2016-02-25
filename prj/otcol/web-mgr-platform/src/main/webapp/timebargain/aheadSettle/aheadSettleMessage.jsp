<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBk" %>
<html>
<head>
<title>
提前交收审核
</title>
</head>
<%
	String applyID = request.getParameter("applyID");
	pageContext.setAttribute("applyID",applyID);

%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<c:url value="/timebargain/aheadSettle/aheadSettleMessageAudit.jsp?applyID=${applyID}"/>"  application="yes">
  <frame name="HiddFrame2"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
