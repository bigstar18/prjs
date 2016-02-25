<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<%
   System.out.println("(String)request.getAttribute('prompt'):"+(String)request.getAttribute("prompt"));
   %>
<script language="javascript">
<c:choose>
  <c:when test="${!empty prompt}">

    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString((String)request.getAttribute("prompt"))%>");  
    document.location.href = "<c:url value="/timebargain/menu/liveInfo.do"/>";
  </c:when>
  <c:otherwise>
    alert("²Ù×÷³É¹¦£¡");
    document.location.href = "<c:url value="/timebargain/apply/applyBill.jsp"/>";
  </c:otherwise>
</c:choose>  
  
</script>

