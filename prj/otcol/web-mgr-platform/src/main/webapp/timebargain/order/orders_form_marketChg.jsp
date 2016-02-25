<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
  var theForm = parent.ListFrame.ordersForm;
  clearOptions(theForm.uni_Cmdty_Code);
<c:choose>
  <c:when test="${!empty prompt}">
    alert("<c:out value="${prompt}" escapeXml="false"/>");   
  </c:when>
  <c:otherwise>   
      <c:forEach items="${commoditySelect}" var="row" varStatus="status">
        addSelectOption(theForm.uni_Cmdty_Code,"<c:out value="${row.label}"/>","<c:out value="${row.value}"/>");
      </c:forEach>
  </c:otherwise>
</c:choose>  
</script>