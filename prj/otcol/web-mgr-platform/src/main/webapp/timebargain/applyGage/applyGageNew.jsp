<%@ include file="/timebargain/common/taglibs.jsp"%>
<%	
basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/timebargain/applyGage/";

%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="68,*,0" border="0">   
  <frame name="TopFrame1" src="<c:url value="/timebargain/applyGage/topApplyGageNew.jsp"/>" scrolling="no"  application="yes">
	<frame name="ListFrame1" src="<%=basePath %>applyGage.do?funcflg=addApplyGage&applyType=1"  application="yes">
	
  <frame name="HiddFrame"  application="yes">
  
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
