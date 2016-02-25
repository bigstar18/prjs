<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<head>

<title>

</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/order/orders.do?funcflg=chkLogin&mkName=password"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
