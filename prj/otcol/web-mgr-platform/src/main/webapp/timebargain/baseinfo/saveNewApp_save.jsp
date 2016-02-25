<%@ include file="/timebargain/common/taglibs.jsp"%>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");    
    window.history.back(-1);
<%
}
else
{
%>
    alert("success!");    
    document.location.href = "<c:url value="/timebargain/baseinfo/virtualFundAppWait.jsp"/>";
<%
}
%> 
</script>
