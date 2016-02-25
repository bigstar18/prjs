<%@ include file="/timebargain/common/taglibs.jsp"%>
<%
String sprompt = (String)session.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(sprompt))
{
    session.removeAttribute("prompt");
	System.out.println("[MESSAGE]" + sprompt);
%>
<script language="javascript">
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(sprompt)%>");
</script>
<%
}
%>
