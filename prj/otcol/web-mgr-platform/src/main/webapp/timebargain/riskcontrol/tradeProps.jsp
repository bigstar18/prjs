<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/riskcontrol/tradeProps_form.jsp">
                                 <c:param name="moduleName" value="${param['moduleName']}"/>
                               </c:url>">
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
