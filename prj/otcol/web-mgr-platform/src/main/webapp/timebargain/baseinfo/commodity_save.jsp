<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");    
    parent.ListFrame.commodityForm.save.disabled = false; 
<%
}
else
{
%>
    alert("�����ɹ���");    
    parent.ListFrame.cancel_onclick();
<%
}
%> 
</script>
