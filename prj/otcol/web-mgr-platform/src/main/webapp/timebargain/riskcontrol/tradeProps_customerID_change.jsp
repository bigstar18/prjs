<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="javascript">
  var theForm = parent.ListFrame.tradePropsForm;
<c:choose>
  <c:when test="${!empty prompt}">
      alert("<c:out value="${prompt}" escapeXml="false"/>");
      theForm.customerID.value = "";
      theForm.customerName.value = "";
	  <c:choose>
		<c:when test="${param['moduleName'] == '1'}">
		    theForm.balance.value = "";
            theForm.inFund.value = "";	
            theForm.outFund.value = "";		
        </c:when>
		<c:when test="${param['moduleName'] == '2'}">
            theForm.virtualFunds.value = "";			
        </c:when>
		<c:when test="${param['moduleName'] == '3'}">
            theForm.maxOverdraft.value = "";				
        </c:when>
		<c:when test="${param['moduleName'] == '4'}">
            theForm.minClearDeposit.value = "";				
        </c:when>
		<c:when test="${param['moduleName'] == '5'}">
            theForm.maxHoldQty.value = "";			
        </c:when>
		<c:otherwise></c:otherwise>
	  </c:choose>      
      theForm.customerID.focus();
  </c:when>
  <c:otherwise>        
      theForm.customerID.value = "<c:out value="${customer.customerID}"/>";
      theForm.customerName.value = "<c:out value="${customer.customerName}"/>";
	  <c:choose>
		<c:when test="${param['moduleName'] == '1'}">
            theForm.balance.value = "<c:out value="${customerFunds.balance}"/>";	
            theForm.inFund.value = "<c:out value="${customerFunds.inFund}"/>";
            theForm.outFund.value = "<c:out value="${customerFunds.outFund}"/>";
        </c:when>
		<c:when test="${param['moduleName'] == '2'}">
            theForm.virtualFunds.value = "<c:out value="${customerFunds.virtualFunds}"/>";			
        </c:when>
		<c:when test="${param['moduleName'] == '3'}">
            theForm.maxOverdraft.value = "<c:out value="${customerTradeProps.maxOverdraft}"/>";				
        </c:when>
		<c:when test="${param['moduleName'] == '4'}">
            theForm.minClearDeposit.value = "<c:out value="${customerTradeProps.minClearDeposit}"/>";			
        </c:when>
		<c:when test="${param['moduleName'] == '5'}">
            theForm.maxHoldQty.value = "<c:out value="${customerTradeProps.maxHoldQty}"/>";		
        </c:when>
		<c:otherwise></c:otherwise>
	  </c:choose>       
  </c:otherwise>
</c:choose>  
</script>