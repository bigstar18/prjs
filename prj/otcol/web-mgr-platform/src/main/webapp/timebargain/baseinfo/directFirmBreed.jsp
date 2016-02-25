<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="91,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/baseinfo/directFirmBreed.do?funcflg=directFirmBreedForwardTop"/>" application="yes">
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/directFirmBreed.do?funcflg= directFirmBreedList&type=e"/>" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff">
</body>
</html>
