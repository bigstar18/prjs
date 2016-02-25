<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK"%>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if("修改成功！".equals(prompt) || "添加成功！".equals(prompt))
	{
		String date = (String)request.getAttribute("date");
		String code = (String)request.getAttribute("code");
		String id = (String)request.getAttribute("id");
		request.setAttribute("date",date);
		request.setAttribute("code",code);
		
	%>
	    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
	    document.location.href = "<c:url value="/timebargain/deduct/deductKeepFirm.jsp?date="/>" + '<%=(String)request.getAttribute("date")%>' + "&code=" + '<%=(String)request.getAttribute("code")%>' + "&id=" + '<%=(String)request.getAttribute("id")%>';
	<%
	}
	else
	{
	%>
		alert("操作失败！");
		document.location.href = "<c:url value="/timebargain/deduct/deductGuide.jsp"/>";
	<%
	}
	%>
	
</script>


