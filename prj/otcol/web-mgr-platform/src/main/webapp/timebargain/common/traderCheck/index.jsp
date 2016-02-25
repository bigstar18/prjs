<%@ include file="/timebargain/common/taglibs.jsp"%>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title>
<fmt:message key="traderCheck.title"/>
</title>
</head>
<frameset rows="*,0" border="0">
  <frame name="MainFrame" src="traderCheck.jsp" scrolling="no">
  <frame name="HiddFrame">
</frameset>
<body bgcolor="#ffffff">
not surport frame
</body>
</html>
