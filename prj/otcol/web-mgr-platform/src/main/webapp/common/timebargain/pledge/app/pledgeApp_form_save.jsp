<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(prompt!=null)
	{
	%>
	    alert("<%=prompt%>");
	<%
	}
	else
	{
	%>
		alert("�����ɹ���");
		document.location.href = "<%=basePath%>/timebargain/pledge/app/pledgeApp.jsp";
	<%
	}
	%>
	
</script>


