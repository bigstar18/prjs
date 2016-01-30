<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/broker/public/includefiles/allincludefiles.jsp"%>
<%
String prompt = (String)request.getAttribute("prompt");
if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
{
%>
<script language="javascript">
    alert("<%=prompt %>");
    parent.ListFrame.frm.order.disabled = false;
</script>
<%
}
%>