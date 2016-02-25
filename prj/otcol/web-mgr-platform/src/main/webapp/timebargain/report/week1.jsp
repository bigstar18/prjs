<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="91,*,0" border="0">   
  <frame name="TopFrame" src="<c:url value="/timebargain/report/report.do">
                                   <c:param name="method" value="topWeekDetail"/>
                               </c:url>" scrolling="no"> 
  <frame name="ListFrame" src="<c:url value="/timebargain/report/week1_list.jsp"/>" >
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
