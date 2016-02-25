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
	    //parent.ListFrame2.virtualFundForm.query.disabled = false;
	    //parent.ListFrame2.virtualFundForm.query1.disabled = false;
	<%
	}
	else
	{
	%>
		alert("²Ù×÷³É¹¦£¡");
		window.dialogArguments.search();
		window.close();
	<%
	}
	%>
	
</script>


