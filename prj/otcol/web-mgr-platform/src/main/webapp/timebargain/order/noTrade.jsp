<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/order/orders.do?funcflg=chkLogin&mkName=noTrade"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
