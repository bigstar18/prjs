<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%
   System.out.println("(String)request.getAttribute('prompt'):"+(String)request.getAttribute("prompt"));
   %>
<script language="javascript">
<c:choose>
  <c:when test="${!empty prompt}">
   
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString((String)request.getAttribute("prompt"))%>");    
  </c:when>
  <c:otherwise>
  </c:otherwise>
</c:choose>  
  //parent.ContFrame.agencyForm.ok.disabled = false;
 // parent.List2Frame.location.href = "<c:url value="/timebargain/statquery/blank.jsp"/>";
   parent.List3Frame.location.href = "<c:url value="/timebargain/statquery/settleListHandHead.jsp"/>";
</script>

