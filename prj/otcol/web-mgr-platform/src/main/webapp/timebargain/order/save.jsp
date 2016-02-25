<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt)) {
%>
	alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>"); 
	document.location.href = "<c:url value='/timebargain/order/orders.do?funcflg=trader_list'/>"
<%
}
%> 
</script>
