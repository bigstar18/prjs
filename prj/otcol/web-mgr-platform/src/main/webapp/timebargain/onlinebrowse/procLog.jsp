<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="66,*,0" border="0">  
  <frame name="TopFrame" src="<c:url value="/timebargain/onlinebrowse/onlinebrowse.do?funcflg=procLog_top"/>" scrolling="no" application="yes">   
  <frame name="ListFrame" src="<c:url value="/timebargain/onlinebrowse/procLog_list.jsp"/>" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
