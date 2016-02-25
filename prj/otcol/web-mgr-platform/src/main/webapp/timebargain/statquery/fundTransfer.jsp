<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="91,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/statquery/statQuery.do?funcflg=topFundTransfer"/>" application="yes" scrolling="no"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/statquery/fundTransfer_list.jsp"/>" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
