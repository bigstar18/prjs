<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
    alert('<%=prompt %>');
    parent.ListFrame.frm.order.disabled = false;
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