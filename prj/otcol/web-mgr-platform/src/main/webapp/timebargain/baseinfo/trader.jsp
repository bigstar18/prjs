<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="*,0" border="0">    
	
 
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/trader.do?funcflg=search">
  							   		<c:param name="firmID" value="${param['firmID']}"/>
  							   </c:url>" >
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
