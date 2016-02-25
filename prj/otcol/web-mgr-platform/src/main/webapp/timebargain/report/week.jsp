<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/report/report.do">
                                   <c:param name="method" value="editWeek"/>   
                                   <c:param name="mkName" value="${param['mkName']}"/>                                
                               </c:url>">
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
