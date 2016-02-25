<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");    
    parent.ListFrame.document.forms(0).save.disabled = false; 
<%
}
else
{
%>
    alert("²Ù×÷³É¹¦£¡");    
    //parent.MainFrame.cancel_onclick();
    document.location.href = "<c:url value="/timebargain/statquery/conferClose.jsp"/>";
   //window.history.back(-1);
<%
}
%> 
</script>
