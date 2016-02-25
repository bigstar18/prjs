<%@ include file="../../common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
	%>
	    alert("<%=prompt%>");
	<%
	}
	else
	{
	%>
		alert("²Ù×÷³É¹¦£¡");
		document.location.href = "<%=basePath%>/timebargain/virtualfund/app/virtualFundApp.jsp";
	<%
	}
	%>
	
</script>


