<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="91,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/report/report.do?funcflg=topHoldDetail" >
                                   <c:param name="mkName" value="${param['mkName']}"/>
                               </c:url>" scrolling="no"  application="yes"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/report/holdDetail_list.jsp"/>"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
