<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<%  System.out.println("#######################"); %>  
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/consigner.do?funcflg=searchConsigner"/>" application="yes">
                               
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>

