<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="javascript">
  var theForm = parent.ListFrame.tradeRuleForm;
<c:choose>
  <c:when test="${!empty prompt}">
      alert("<c:out value="${prompt}" escapeXml="false"/>");
      theForm.customerID.value = "";
      theForm.customerID.focus();
  </c:when>
  <c:otherwise>        
      theForm.customerID.value = "<c:out value="${customer.customerID}"/>";
  </c:otherwise>
</c:choose>  
</script>