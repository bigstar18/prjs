<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
查看审核虚拟资金
</title>
</head>

<%
	String id = request.getParameter("id");
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<%=basePath%>/timebargain/virtualfund/app/virtualFundChe_form_App.jsp?id=<%=id%>" application="yes">
  <frame name="HiddFrame2" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
