<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<script language="javascript">
<c:choose>
  <c:when test="${!empty prompt}">
    pTop("<c:url value="/timebargain/order/traderLogin.jsp"/>",400,300);
  </c:when>
  <c:otherwise>
    chkOk();
  </c:otherwise>
</c:choose>  

function chkOk()
{
	<c:choose>
		<c:when test="${param['mkName'] eq 'noTrade'}">
			parent.ListFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=noTradeList"/>";
		</c:when>
		<c:when test="${param['mkName'] eq 'order'}">
			parent.ListFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=edit"/>";
		</c:when>
		<c:when test="${param['mkName'] eq 'password'}">
			parent.ListFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=password"/>";
		</c:when>
		<c:when test="${param['mkName'] eq 'conferClose'}">
			parent.ListFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=editConferClose"/>";
		</c:when>
		<c:when test="${param['mkName'] eq 'forceClose'}">
			parent.ListFrame.location.href = "<c:url value="/timebargain/tradecontrol/tradeCtl.do?funcflg=searchForceClose"/>";
		</c:when>
		<c:when test="${param['mkName'] eq 'detailForceClose'}">
			parent.ListFrame.location.href = "<c:url value="/timebargain/tradecontrol/tradeCtl.do?funcflg=searchDetailForceClose"/>";
		</c:when>
		<c:otherwise>
			parent.ListFrame.order();
		</c:otherwise>
	</c:choose>	
}

</script>

