<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");

if("操作成功".equals(prompt))
{
%>
    alert("<%=prompt%>");
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
</script>