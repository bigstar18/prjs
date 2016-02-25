<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
		
	%>
	    alert("²Ù×÷³É¹¦£¡");
	    
	<%
	}
	else
	{
	%>
		alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
	<%
	}
	%>
	document.location.href = "<c:url value="/timebargain/deduct/deductWrite_form.jsp"/>";
</script>


                                                                                                                                                                                                                                                                                                                                                                                                                                                                         