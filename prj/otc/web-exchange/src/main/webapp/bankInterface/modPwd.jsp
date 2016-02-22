<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<%@ include file="session.jsp"%>

<%
String uid = (String)session.getAttribute("uid");

if(request.getParameter("submitFlag") != null && request.getParameter("submitFlag").equals("do"))
{
	Connection conn = null;
	PreparedStatement ps = null;
	int result = 0;	
	String pwd = request.getParameter("pwd");

	try{
		 BankDAO dao = BankDAOFactory.getDAO();
		 conn = dao.getConnection();
			
			String sqlStr = "update manager set password='"+pwd+"' where userid='"+uid+"'";
			
			ps = conn.prepareStatement(sqlStr);
			ps.executeUpdate();
			
			ps.close();
			ps = null;	
		
	}catch(Exception e){
		e.printStackTrace();
		result = -1;
	}finally{
		if(ps!=null){try{ps.close();}catch(Exception ex){}ps=null;}
		try
		{
		  conn.close();
		}
		catch (Exception e) 
		{    	
		}
		conn = null;
	}
	

	if(result == 0)
	{
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("管理员密码修改成功！");
			window.opener.location.reload();
			window.close();
			//-->
			</SCRIPT>	
		<%
		return;
	}
	else
	{
		%>
		<SCRIPT LANGUAGE="JavaScript">
			<!--
			alert("管理员密码修改失败！");
			//-->
			</SCRIPT>	
		<%
	}

}
%>

<html xmlns:MEBS>
  <head>
	<META http-equiv="Content-Type" content="text/html; charset=GBK">	
	<link rel="stylesheet" href="skin/default/css/style.css" type="text/css"/>
	<script language="javascript" src="lib/tools.js"></script>
    <title>修改管理员口令</title>
  </head>
  
  <body>
  	<form id="frm" action="" method="post">
		<fieldset width="95%">
			<legend>修改管理员口令</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="35">
				<tr height="35">
					<td align="right">新口令：&nbsp;</td>
					<td align="left">
						<input name="pwd" value="" type=password  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">
					<td align="right">新口令确认：&nbsp;</td>
					<td align="left">
						<input name="pwd1" value="" type=password  class="text" maxlength="10" style="width: 140px"><span class=star>*</span>
					</td>
				</tr>
				<tr height="35">					
					<td align="center" colspan=2>
						<button type="button" class="smlbtn" onclick="doMod();">修改</button>&nbsp;
						<button type="button" class="smlbtn" onclick="window.close();">取消</button>&nbsp;
						<input type=hidden name=submitFlag value="">
					</td>
				</tr>
			</table>
		</fieldset>	  
	</from>
  </body>
</html>

<SCRIPT LANGUAGE="JavaScript">
<!--

function doMod()
{
	if(trim(frm.pwd.value) == "")
	{
		alert("请输入新口令！");
		frm.pwd.focus();
	}
	else if(frm.pwd.value != frm.pwd1.value)
	{
		alert("口令不一致！");
		frm.pwd.focus();
	}
	else
	{
		frm.submitFlag.value = "do";
		frm.submit();
	}	
}

//-->
</SCRIPT>