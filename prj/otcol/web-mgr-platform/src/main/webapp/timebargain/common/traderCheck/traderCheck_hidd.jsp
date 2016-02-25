<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="javascript">
<c:choose>
  <c:when test="${!empty prompt}">
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString((String)request.getAttribute("prompt"))%>");    
    parent.MainFrame.ordersForm.ok.disabled = false;
  </c:when>
  <c:otherwise>
    alert("<fmt:message key="traderLogin.success"/>");
    parent.MainFrame.closewindow();
  </c:otherwise>
</c:choose>  
</script>

