<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<c:choose>
  <c:when test="${!empty prompt}">
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString((String)request.getAttribute("prompt"))%>");    
    parent.ListFrame.ordersForm.ok.disabled = false;
  </c:when>
  <c:otherwise>
  	parent.ListFrame.closewindow();
  </c:otherwise>
</c:choose>  
</script>

