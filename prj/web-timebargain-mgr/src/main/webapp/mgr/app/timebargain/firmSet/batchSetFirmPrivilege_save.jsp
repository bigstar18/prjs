<%@ page pageEncoding="GBK" %>
<script language="javascript">
<%
String prompt = (String)request.getAttribute("prompt");

if("�����ɹ�".equals(prompt))
{
%>
    alert("<%=prompt%>");
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
</script>