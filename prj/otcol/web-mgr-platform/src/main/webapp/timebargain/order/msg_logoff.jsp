<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<%
String mkName = (String)request.getAttribute("mkName");
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
<script language="javascript">
	var mkName = "<%=mkName%>";
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
    parent.ListFrame.location.href = "<c:url value="/timebargain/order/orders.do?funcflg=chkLogin&mkName="/>" + mkName;
</script>
<%
}
%>