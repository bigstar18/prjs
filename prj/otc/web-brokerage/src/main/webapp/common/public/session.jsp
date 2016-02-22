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
<%@ include file="choSkin.jsp"%>
<%!
public static final int PAGESIZE = 10;
%>
<link rel="stylesheet" href="<%=skinPath%>/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/main.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/button.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/print.css" type="text/css"/>
<link rel="stylesheet" href="<%=skinPath%>/report.css" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="<%=skinPath%>/xtree.css" />
<link rel="stylesheet" type="text/css" href="<%=skinPath%>/common1.css"/>