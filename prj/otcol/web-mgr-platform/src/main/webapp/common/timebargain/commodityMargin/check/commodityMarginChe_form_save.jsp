<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(prompt!=null)
	{
	%>
	    alert("<%=prompt%>");
	    parent.ListFrame2.relase();
	<%
	}
	else
	{
	%>
		alert("�����ɹ���");
		window.dialogArguments.search();
		window.close();
		
	<%
	}
	%>
	
</script>


