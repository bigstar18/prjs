<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="globalDef.jsp"%>
<style type="text/css">
<!--
body {
	background-color: #6b7ea9;
	text-align: center;
	vertical-align: middle;
	margin-top: 100px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left: 0px;
}
.login_td {
	background-color: #CFD9F1;
	height: 298px;
	width: 490px;
	background-image: url(pic/bg.jpg);
	background-repeat: no-repeat;
	border: 1px dashed #FFFFFF;
	background-attachment: fixed;
	background-position: center bottom;
	margin: 0px;
	text-align: center;
	vertical-align: top;
}
.login_bt {
	font-size: 12px;
	font-weight: normal;
	color: #FFFFFF;
	text-decoration: none;
	background-color: #6B7EA9;
	padding-top: 0px;
	padding-right: 5px;
	padding-bottom: 0px;
	padding-left: 5px;
	border-right-width: 1px;
	border-left-width: 1px;
	border-right-style: solid;
	border-left-style: solid;
	border-right-color: #414F70;
	border-left-color: #414F70;
	text-align: center;
}
.login_k {
	background-color: #FFFFFF;
	border: 1px dashed #6B7EA9;
	height: 18px;
	width: 140px;
}
.login_btn {
	font-size: 12px;
	font-weight: normal;
	color: #FFFFFF;
	text-decoration: none;
	background-color: #6B7EA9;
	border-top-width: 1px;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #6B7EA9;
	border-right-color: #3D4A69;
	border-bottom-color: #3D4A69;
	border-left-color: #6B7EA9;
	padding-top: 2px;
	padding-right: 2px;
	padding-bottom: 0px;
	padding-left: 2px;
}
-->
</style>

<%
if(request.getParameter("Submit") != null)
{		
		//out.println(delNull(request.getRemoteAddr()));
		//验证码校验	
		String randNumSys = (String)session.getAttribute("RANDOMICITYNUM");
		String randNumInput = request.getParameter("randNumInput");
		if(!(randNumInput != null && !randNumInput.trim().equals("") && randNumInput.trim().equals(randNumSys)))
		{
			alert("验证码错误！",out);
		}
		else //口令校验
	    {
			String username = delNull(request.getParameter("username"));
			String password = delNull(request.getParameter("password"));
			
			LogonManager manager = LogonManager.getInstance();
			TraderInfo traderInfo= manager.logon(username, password, "", request.getRemoteAddr());
				/*
				sessionID；-1：交易员代码不存在；-2：口令不正确；-3：禁止登陆；
				-4：Key盘验证错误；-5：其他异常 -6交易板块被禁止
				*/	
				if(traderInfo.auSessionId>0)
				{
					session.setAttribute("LOGINID",new Long(traderInfo.auSessionId));		
					session.setAttribute("username",username);
					session.setAttribute("FIRMID",traderInfo.firmId);
					session.setMaxInactiveInterval(SESSIONINTERVAL);		
				    sendRedirect("main.jsp",out);
				  return;
				}	
				else if(traderInfo.auSessionId == -1)
				{
					alert("交易员代码不存在！",out);	
				}
				else if(traderInfo.auSessionId == -2)
				{
					alert("口令不正确！",out);	
				}	
				else if(traderInfo.auSessionId == -3)
				{
					alert("禁止登陆！",out);	
				}	
				else if(traderInfo.auSessionId == -4)
				{
					alert("Key盘验证错误！",out);	
				}
				else if(traderInfo.auSessionId == -5)
				{
					alert("登录失败！",out);	
				}
				else
				{
					alert("交易板块被禁止！",out);
				}
		}			
}
%>
	<!--<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
      STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
      CODEBASE="GnntKey.cab#Version=1,0,0,5">
	</OBJECT> -->


<body bgcolor="#CEDEDE">
	<form name="frm" method="post" action="">
<table width="492"  border="0"  cellpadding="0" cellspacing="0">
    <tr>
      <td><div align="center"><!--img src="pic/m_name.jpg" width="307" height="56" /--></div></td>
    </tr>
      <tr>
        <td width="506" class="login_td">
        <table width="100%" height="88" border="0" cellpadding="0" cellspacing="0">
        <tr>
        <td height="88">         
          <div align="center"><b>欢 迎 登 录 出 入 金 系 统</b><!--img src="pic/bt.jpg" width="268" height="31" /--></div>
          </td>
          </tr>
          </table>
		  <table width="55%" border="0" cellpadding="0" cellspacing="10">
            <tr>
              <td width="33%" class="login_bt">用户名</td>
              <td width="67%"><input name="username" type="text" class="login_k"></td>
            </tr>
            <tr>
              <td class="login_bt">密&nbsp;&nbsp;码</td>
              <td><input name="password" type="password" class="login_k"></td>
            </tr>
			<tr>
              <td class="login_bt">验证码</td>
              <td><input name=randNumInput type="text" class="login_k">&nbsp;<img src="image.jsp" align="absmiddle"></td>
            </tr>
            <tr>              
              <td height="52">&nbsp;</td>
              <td><label>
			      <input type="submit" name="Submit" onclick="return frmChk()" class="login_btn" value="提交">&nbsp;&nbsp;
				  <input type="reset" name="Submit2"  class="login_btn" value="重置">
			  </label>
			  </td>
            </tr>
        </table>
		</td>
        </tr>
    </table>
<input type=hidden name="kcode">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	if(frm.username.value == "")
	{
		alert("请填写用户名！");
		frm.username.focus();
		return false;
	}
	else if(frm.randNumInput.value == "")
	{
		alert("请填写验证码！");
		frm.randNumInput.focus();
		return false;
	}
	return true;
}
frm.username.focus();
//-->
</SCRIPT>