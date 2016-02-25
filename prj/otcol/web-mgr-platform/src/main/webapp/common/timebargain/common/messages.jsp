<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
	System.out.println("[MESSAGE]" + prompt);
%>
<script language="javascript">
    alert('<%=prompt%>');
    <c:if test="${!empty param['retLocation']}">
      <c:out value="${param['retLocation']}"/>;
    </c:if>    
</script>
<%
}
%>