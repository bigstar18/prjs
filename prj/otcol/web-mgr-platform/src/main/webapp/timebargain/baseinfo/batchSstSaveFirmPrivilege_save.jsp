<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");

if("�����ɹ�".equals(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
    window.close();
<%
}
else
{
%>
    alert("����ʧ�ܣ�");    
    
<%
}
%> 

	//document.location.href = "<c:url value="/timebargain/baseinfo/customer.jsp"/>";
</script>
