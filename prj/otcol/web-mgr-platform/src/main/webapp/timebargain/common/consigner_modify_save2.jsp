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
		alert("������ĳɹ���");
		//parent.ListFrame.cancel_onclick();
		parent.location.href = "<c:url value="/timebargain/menu/DLWT_password.do"/>";
		//window.dialogArguments.cancel_onclick();
	<%
	}
	%>
	
</script>


