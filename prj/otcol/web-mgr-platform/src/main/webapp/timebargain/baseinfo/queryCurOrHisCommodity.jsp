<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="81,*,0" border="0">    
  <frame name="TopFrame" src="<c:url value="/timebargain/baseinfo/queryCommodity_top.jsp">
                                 <c:param name="oper" value="${param['oper']}"/>
                               </c:url>" scrolling="no"  application="yes"> 
  <frame name="ListFrame2" src="<c:url value="/timebargain/baseinfo/queryCommodity.do?funcflg=queryCommodity">
                                 <c:param name="oper" value="${param['oper']}"/>
                               </c:url>" scrolling="auto"  application="yes">
  <frame name="HiddFrame"  application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>

