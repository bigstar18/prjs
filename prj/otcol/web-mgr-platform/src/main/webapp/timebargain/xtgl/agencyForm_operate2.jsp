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
    alert("²Ù×÷³É¹¦£¡");
  </c:otherwise>
</c:choose>  
  //parent.ContFrame.agencyForm.ok.disabled = false;
   
   window.dialogArguments.recieveTime(parent.ContFrame2.agencyForm.recoverTime.value);
   window.close();
   parent.ContFrame.location.reload();
</script>

