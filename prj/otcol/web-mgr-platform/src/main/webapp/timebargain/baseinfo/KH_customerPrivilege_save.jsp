<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
String customerID = (String)request.getAttribute("customerID");
if("操作成功".equals(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
    window.dialogArguments.searchKHPri();
    window.close();
<%
}
else
{
%>
    alert("操作失败！");    
    
<%
}
%> 

	//document.location.href = "<c:url value="/timebargain/baseinfo/customer.jsp"/>";
</script>
