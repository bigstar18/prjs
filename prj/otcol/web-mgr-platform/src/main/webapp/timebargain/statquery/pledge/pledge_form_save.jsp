<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>
<script language="javascript">
	<%
	String prompt = (String)request.getAttribute("prompt");
	if(!org.apache.commons.lang.StringUtils.isEmpty(prompt))
	{
	%>
	    alert("<%=gnnt.MEBS.timebargain.manage.util.StringFormat.getAlertString(prompt)%>");
	    parent.ContFrame.statQueryForm.save.disabled = false;
	<%
	}
	else
	{
	%>
		alert("�����ɹ���");
		//parent.ListFrame.cancel_onclick();
		top.mainFrame.location.href = "<c:url value="/timebargain/menu/Pledge.do"/>";
	<%
	}
	%>
	
</script>


