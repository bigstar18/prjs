<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
	%>
	    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
	    //parent.ListFrame.customerForm.save.disabled = false;
	<%
	}
	else
	{
	%>
		alert("口令更改成功！");
		//parent.ListFrame.cancel_onclick();
		window.dialogArguments.cancel_onclick();
		window.close();
	<%
	}
	%>
	
</script>


