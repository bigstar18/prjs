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
<title>强制转让信息</title>
</head>
<frameset rows="*,0" border="0">
  <c:if test="${param['fcFlag'] == 1}">   
  <frame name="ListFrame" src="<c:url value="/timebargain/tradecontrol/detailForceClose_list_qp_form.jsp">
  								 <c:param name="FirmID" value="${param['FirmID']}"/>
                                 <c:param name="CommodityID" value="${param['CommodityID']}"/>
                                 <c:param name="BS_Flag" value="${param['BS_Flag']}"/>
                                 <c:param name="HoldQty" value="${param['HoldQty']}"/>                                 
                                 <c:param name="openQty" value="${param['openQty']}"/>                                
                               </c:url>" application="yes">
  </c:if> 
   <c:if test="${param['fcFlag'] != 1 }">   
  <frame name="ListFrame" src="<c:url value="/timebargain/tradecontrol/forceClose_list_qp_form.jsp">
  								 <c:param name="FirmID" value="${param['FirmID']}"/>
                                 <c:param name="CommodityID" value="${param['CommodityID']}"/>
                                 <c:param name="BS_Flag" value="${param['BS_Flag']}"/>
                                 <c:param name="HoldQty" value="${param['HoldQty']}"/>                                 
                                 <c:param name="EvenPrice" value="${param['EvenPrice']}"/>                                 
                               </c:url>" application="yes">
  </c:if>
  <frame name="HiddFrame" application="yes">
</frameset>
<body bgcolor="#ffffff" >
</body>
</html>
