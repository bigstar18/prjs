<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
ÐÞ¸ÄÃÜÂë
</title>
</head>

<%
	String prikey = request.getParameter("prikey");
	pageContext.setAttribute("prikey",prikey);
%>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame2" src="<c:url value="/timebargain/common/modifyPwd1.jsp?type=consigner&prikey=${prikey}"/>" application="yes">
                               
  <frame name="HiddFrame2" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>

