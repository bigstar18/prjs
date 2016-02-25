<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="31,*,0" border="0">    
  <frame name="HeadFrame" src="<c:url value="/timebargain/baseinfo/commodity_head.jsp">
                                 <c:param name="breedID" value="${param['breedID']}"/>
                                 <c:param name="condition" value="${param['condition']}"/>
                               </c:url>" scrolling="no" application="yes"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/commodity.do">
                                 <c:param name="breedID" value="${param['breedID']}"/>
                                 <c:param name="condition" value="${param['condition']}"/>
                               </c:url>" scrolling="auto" application="yes">
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>

