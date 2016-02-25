<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<html>
<head>
<title>
审核商品手续费
</title>
</head>

<%
	String id = request.getParameter("id");
	pageContext.setAttribute("id",id);
%>
<frameset rows="*,0" border="0">   
  <frame name="ListFrame2" src="<%=basePath%>/timebargain/commodityFee/check/commodityFeeChe_form.jsp?id=${id}" application="yes">
  <frame name="HiddFrame2" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
