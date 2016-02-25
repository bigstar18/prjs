<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="gnnt.MEBS.timebargain.manage.service.SettleManageDelayPattern" %>
<base target="_self">
<%
	String matchID = request.getParameter("matchID");
	String userid = gnnt.MEBS.common.security.AclCtrl.getLogonID(request);
 	int checkResult=SettleManageDelayPattern.verifySettle(matchID, userid);
	
	if(checkResult==-5)
	{
		%>
			<script type="text/javascript">
				alert("状态已经不能操作!");
				window.close();
			</script>
		<%
	}
	else if(checkResult==-6)
	{
		%>
			<script type="text/javascript">
				alert("操作异常!");
				window.close();
			</script>
		<%
	}
	else if(checkResult>-5 && checkResult<0)
	{
		%>
			<script type="text/javascript">
				alert("审核失败!");
				window.close();
			</script>
		<%
	}
	else
	{
	%>
			<script type="text/javascript">
				alert("审核成功!");
				window.returnValue="-1";
				window.close();
			</script>
		<%	
	}	
%>