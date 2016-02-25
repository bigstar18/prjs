<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="88,*,0" border="0" id="CustomerMain">    
  <frame name="TopFrame" src="<c:url value="/timebargain/baseinfo/firm.do?funcflg=top"/>" scrolling="auto" application="yes"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/firm.do"/>" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
