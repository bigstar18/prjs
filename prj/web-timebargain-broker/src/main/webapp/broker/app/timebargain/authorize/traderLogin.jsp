<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
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
��Ϊί��Ա��¼
</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="${basePath}/broker/app/timebargain/authorize/traderLogin_form.jsp"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>