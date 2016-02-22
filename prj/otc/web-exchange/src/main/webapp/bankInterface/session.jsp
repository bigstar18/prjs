<%
if(session.getAttribute("LOGINIDS") == null)
{
	%>
	<SCRIPT LANGUAGE="JavaScript">
	<!--
	//window.location = "<%=request.getContextPath()%>/common/logon.jsp";
	//-->
	</SCRIPT>
	<%
	//return;
}
%>
<%@ include file="../public/session.jsp"%>
