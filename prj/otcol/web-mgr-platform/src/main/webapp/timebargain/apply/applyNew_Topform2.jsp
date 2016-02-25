<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="80,*,0" border="0">   
  <frame name="TopFrame2" src="<c:url value="/timebargain/apply/applyNew_queryForm2.jsp">
  									<c:param name="applyType" value="${param['applyType']}"/>
  							   </c:url>" scrolling="no" application="yes">
	<frame name="ListFrame2" src="<c:url value="/timebargain/apply/applyNewRelForm2.jsp">
										<c:param name="applyType" value="${param['applyType']}"/>
								  </c:url>" application="yes">
	
  <frame name="HiddFrame" application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
