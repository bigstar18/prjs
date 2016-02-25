<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<%
	String weeks = request.getParameter("weeks");
	pageContext.setAttribute("weeks",weeks);
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=editDaySection&weeks=${weeks}"/>" application="yes"> 
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
