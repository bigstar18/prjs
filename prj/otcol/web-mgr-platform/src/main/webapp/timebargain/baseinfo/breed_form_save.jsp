<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<c:choose>
  <c:when test="${!empty prompt}">
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString((String)request.getAttribute("prompt"))%>");
    document.location.href = "<c:url value="/timebargain/baseinfo/breed.jsp"/>"; 
  </c:when>
  <c:otherwise> 
      alert("�����ɹ���");
      document.location.href = "<c:url value="/timebargain/baseinfo/breed.jsp"/>";
  </c:otherwise>
</c:choose>  
</script>