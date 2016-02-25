<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if("生成强减数据成功！".equals(prompt))
	{
		
	%>
	    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
	    document.location.href = "<c:url value="/timebargain/deduct/nextDeductDetail.jsp?date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>' + "&qty=" + '<%=(String)request.getAttribute("deductQty")%>' + "&id=" + '<%=(String)request.getAttribute("id")%>';
	<%
	}
	else
	{
	%>
		alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
		document.location.href = "<c:url value="/timebargain/deduct/deductKeepFirm.jsp?date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>' + "&id=" + '<%=(String)request.getAttribute("id")%>';
	<%
	}
	%>
	
</script>


