<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">

  var theForm = parent.ListFrame.broadcastForm;
<c:choose>
  <c:when test="${!empty prompt}">
      alert("<c:out value="${prompt}" escapeXml="false"/>");
      theForm.customerID.value = "${customerID}";
      theForm.name.value = "";
      theForm.customerID.focus();
  </c:when>
  <c:when test="${!empty customerList}"> 
  theForm.customerID.value = "<c:out value="${customerID}"/>"; 
  </c:when>
  <c:otherwise>      

      theForm.customerID.value = "<c:out value="${customer.customerID}"/>";
      theForm.name.value = "<c:out value="${customer.customerName}"/>";
  </c:otherwise>
</c:choose>  
</script>