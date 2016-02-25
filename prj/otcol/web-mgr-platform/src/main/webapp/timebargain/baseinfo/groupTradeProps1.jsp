<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="88,*,0" border="0" id="CustomerMain">    
  <frame name="TopFrame" src="<c:url value="/timebargain/baseinfo/tradeProps.do?funcflg=topGroupProps"/>" scrolling="auto"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/tradeProps.do?funcflg=searchGroupProps"/>" >
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
