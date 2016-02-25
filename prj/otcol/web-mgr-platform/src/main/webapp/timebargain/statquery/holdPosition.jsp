<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="116,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/statquery/statQuery.do?funcflg=topHoldPosition"/>" scrolling="no"  application="yes"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/statquery/holdPosition_list.jsp"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
