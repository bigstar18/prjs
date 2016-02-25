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
已用后缀客户ID映射
</title>
</head>
<%
  //request.setCharacterEncoding("GBK");
  //System.out.println("request.getParameter('marketName'):"+request.getParameter("marketName"));
 %>
<frameset rows="*,0" border="0">    
  <frame name="ListFrame" src="<c:url value="/timebargain/baseinfo/customerMap.do?funcflg=listUsedSuffixMap">
                                     <c:param name="marketCode" value="${param['marketCode']}"/>  
                                     <c:param name="marketName" value="${param['marketName']}"/> 
                                     <c:param name="firmID" value="${param['firmID']}"/>                                  
                               </c:url>" scrolling="no">
  <frame name="HiddFrame" >
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
