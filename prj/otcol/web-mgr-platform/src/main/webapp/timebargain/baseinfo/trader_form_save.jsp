<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
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
    alert("�����ɹ���");    
    
<%
}
%> 
	document.location.href = "<c:url value="/timebargain/baseinfo/customerMgr.jsp"/>";
</script>
