<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
		
	%>
	    alert("²Ù×÷³É¹¦£¡");
	    //top.mainFrame.location.href = "<c:url value="/timebargain/deduct/deductGuide.jsp"/>";
	    document.location.href = "<c:url value="/timebargain/deduct/deductPositionQuery.jsp"/>";
	<%
	}
	else
	{
	%>
		alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
		document.location.href = "<c:url value="/timebargain/deduct/deductGuide.jsp"/>";
	<%
	}
	%>
	
</script>


                                                                                                                                                                                                                                                                                                                                                                                                                                                                         