<%@ include file="/timebargain/common/taglibs.jsp"%>
<html>
<head>
<title>
default
</title>
</head>
<frameset rows="*" cols="160,8,*,0" border="0" id="GroupMain">

  <frameset rows="*,0" frameborder="NO" border="0" framespacing="0">
  	<frame name="GroupTreeFrame" src="<c:url value="/timebargain/baseinfo/customerGroup.do?funcflg=groupTree"/>" scrolling="auto" noresize>
  	<frame name="HiddGroupTreeFrame">
  </frameset>
  
  <frame name="SwapHidd" src="<c:url value="/timebargain/baseinfo/customerGroup_swaphidd.html"/>" scrolling="no">
  
  <frameset rows="*,0" cols="*" framespacing="0" frameborder="NO" border="0">
    <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/customerGroup.do"/>" >
    <frame name="HiddListFrame" >
  </frameset>

  <frame name="HiddCustomerGroupFrame">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>


