<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="91,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/member/brokerReportController.mem?funcflg=topTradeFeeBroker"/>" scrolling="no"  application="yes"> 
  <frame name="ListFrame" src="<c:url value="./tradeFeeBroker_list.jsp"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
