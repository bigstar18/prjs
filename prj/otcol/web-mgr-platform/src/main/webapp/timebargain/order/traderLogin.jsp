<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title>
代为委托员登录
</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/order/traderLogin_form.jsp"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>