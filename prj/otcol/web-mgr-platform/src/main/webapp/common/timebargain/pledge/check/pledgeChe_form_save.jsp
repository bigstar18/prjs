<%@ include file="/common/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
	%>
	    alert("<%=prompt%>");

	    parent.ListFrame2.relase();
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


