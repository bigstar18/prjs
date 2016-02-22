<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="./session.jsp"%>
<html>
	<head>
		<META http-equiv="Content-Type" content="text/html; charset=GBK">
		<link rel="stylesheet" href="./skin/default/css/style.css" type="text/css"/>
		<script language="javascript" src="./lib/tools.js"></script>
		<title>财务系统</title>
		<style type="text/css">
		<!--
		.top_bt {
			font-size: 14px;
			color: #FFFFFF;
			text-decoration: none;
			font-weight: bold;
			line-height: 30px;
		}
		-->
		</style>
	</head>

	<%
	if(request.getParameter("logFlag") != null && request.getParameter("logFlag").equals("do"))
	{
		try{
			session.removeAttribute("uid");
			session.invalidate();
			%>
			<SCRIPT LANGUAGE="JavaScript">
			<!--
			window.parent.location = "login.jsp";
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
			window.parent.location = "login.jsp";
			//-->
			</SCRIPT>
			<%
			return;
		}
	}
	%>

	<body class="topframe">
	<form name=frm>
		<div style="width: 100%; height: 55px; overflow: hidden;">
			<div style="width: 100%; height: 50px; overflow: hidden; background-color: #6A7EA8;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%"  height="100%">
					<tr>
					    <td width="80" bgcolor="6B7EA9" align=center><img src="skin/default/images/logo.jpg" width="50" height="50" onclick="goMain()" style="cursor:hand"></td>
					    <td valign="bottom" bgcolor="6B7EA9"><span class="top_bt">金网安泰银行接口管理系统</span></td>
					    <td width="80" valign="bottom" bgcolor="6B7EA9">&nbsp;</td>
					    <td width="69" valign="bottom" bgcolor="6B7EA9"><div align="center"><img src="skin/default/images/modify.gif" width="69" height="19" border="0" onclick="PopWindow('modPwd.jsp',400,180);" style="cursor:hand" title="修改用户口令"></div></td>
						<td width="44" valign="bottom" bgcolor="6B7EA9"><div align="center"><img src="skin/default/images/out.gif" width="44" height="19" border="0" onclick="logout();" style="cursor:hand" title="退出本系统"></div></td>
					</tr>
				</table>
			</div>
			<div style="width: 100%; height: 1px; overflow: hidden; background-color: #7F8EAD;">
			</div>
			<div style="widht: 100%; height: 4px; overflow: hidden; background-color: #929DB4;">
			</div>
		</div>
		<input type=hidden name=logFlag value="">
		</form>
	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--
function logout()
{
	frm.logFlag.value = "do";
	frm.submit();
}
//-->
</SCRIPT>