<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
<script language="javascript">
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
    parent.ListFrame.ordersForm.order.disabled = false;
</script>
<%
}
%>