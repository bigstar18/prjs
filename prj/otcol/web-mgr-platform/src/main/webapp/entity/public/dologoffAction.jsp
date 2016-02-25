<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../globalDef.jsp"%>
<!--用户身份判断-->
<%@ include file="session.jsp"%>
<%

try
{	
	int idx = toInt(request.getParameter("idx"));
		System.out.println(request.getParameter("logoff"));
	if(request.getParameter("logoff") != null && request.getParameter("logoff").equals("dooff"))
	{
		/*用户注销_start*/

		try{
			session.removeAttribute("LOGINID1");
			session.invalidate();
			%>
			<SCRIPT LANGUAGE="JavaScript">
			<!--
			window.parent.location = "../login.jsp";
			//-->
			</SCRIPT>
			<%
			return;
		}
		catch(Exception e1)
		{
			System.out.println(e1.toString());
			%>
			<SCRIPT LANGUAGE="JavaScript">
			<!--
			window.parent.location = "../login.jsp";
			//-->
			</SCRIPT>
			<%
			return;
		}
		/*用户注销_end*/
	}
}
catch(Exception e1)
		{
	System.out.println(e1.toString());
	}
%>

