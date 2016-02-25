<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="100,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/statquery/statQuery.do?funcflg=topSettle"/>" scrolling="no" application="yes">
	<frame name="List2Frame" src="<c:url value="/timebargain/statquery/settle_listSettle.jsp"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
