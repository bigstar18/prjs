<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="70,*,0" border="0">   
  <frame name="TopFrame1" src="<c:url value="/timebargain/apply/topApplyWaitCheck.jsp"/>" scrolling="no"  application="yes">
	<frame name="ListFrame1" src="<c:url value="/timebargain/apply/applyWaitCheck_list.jsp"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
