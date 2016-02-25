<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="80,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/statquery/topTogether.jsp"/>" scrolling="no" application="yes">
	<frame name="List5Frame" src="<c:url value="/timebargain/statquery/statQuery.do?funcflg=listTogether"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
