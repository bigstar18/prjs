<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="134,*,0" border="0">    
<frame name="TopFrame" src="<c:url value="/timebargain/tradecontrol/tradeCtl.do?funcflg=topHoldPosition"/>" scrolling="yes"  application="yes"> 
<frame name="ListFrame" src="<c:url value="/timebargain/tradecontrol/hold_list.jsp"/>"  application="yes"> 

  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
