<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="javascript">
  var commodityForm = parent.ListFrame.commodityForm;
  clearOptions(commodityForm.breedID);
<c:choose>
  <c:when test="${!empty prompt}">
    alert("<c:out value="${prompt}" escapeXml="false"/>");   
  </c:when>
  <c:otherwise>   
      <c:forEach items="${breedSelect}" var="row" varStatus="status">
        addSelectOption(commodityForm.breedID,"<c:out value="${row.label}"/>","<c:out value="${row.value}"/>");
      </c:forEach>
      if(top.MainFrame.HeadFrame.breedID.value != "")
      {
      	commodityForm.breedID.value = top.MainFrame.HeadFrame.breedID.value;
        parent.ListFrame.breedID_onchange();
      }
  </c:otherwise>
</c:choose>  
</script>