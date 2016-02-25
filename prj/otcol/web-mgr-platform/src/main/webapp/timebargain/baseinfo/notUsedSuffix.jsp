<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%
request.setCharacterEncoding("GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title>
Î´ÓÃºó×º
</title>
</head>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/suffix.do?funcflg=listNotUsedSuffix">
                                     <c:param name="marketCode" value="${param['marketCode']}"/>                                   
                               </c:url>" scrolling="no">
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
