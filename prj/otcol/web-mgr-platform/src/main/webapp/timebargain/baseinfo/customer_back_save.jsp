<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>"); 
       
<%
}
else
{
%>
    alert("<fmt:message key="message.operatesuccess"/>");    
    
<%
}
%> 

	document.location.href = "<c:url value="/timebargain/baseinfo/customer.jsp"/>";
</script>
