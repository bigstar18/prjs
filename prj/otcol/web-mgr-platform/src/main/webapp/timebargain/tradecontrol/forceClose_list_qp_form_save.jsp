<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
String expireMessage = (String)request.getAttribute("expireMessage");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
    parent.ListFrame.ordersForm.order.disabled = false;
<%
}
else if(!org.apache.commons.lang.StringUtils.isEmpty(expireMessage))
{
%>
	alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(expireMessage)%>");
	parent.ListFrame.close_close();
<%	
}
else
{
%>
    alert("²Ù×÷³É¹¦£¡");
    parent.ListFrame.close_close();
<%
}
%>
</script>