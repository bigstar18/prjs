<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/public/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>长三角商品交易所(模拟)</title>
<link href="css.css" rel="stylesheet" type="text/css" />
</head>
<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
      STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
      CODEBASE="GnntKey.cab#Version=1,0,0,5">
</OBJECT>
<body class="index_bg">
 <form name="frm" method="post" action="<%=basePath %>/logon/logon.action">
	<table width="628" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="173">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><img src="<%=skinPath%>/cssimg/index_logo.jpg" /></td>
  </tr>
  <tr>
    <td class="st_bor"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="299"><img src="<%=skinPath%>/cssimg/index_pic.jpg" width="299" height="321" /></td>
        <td class="index_midright"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="100">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td width="30%" align="right" class="index_w12">用户名：</td>
            <td><input type="text" name="username" size="18" onkeydown="keyEnter();"/>
            </td>
          </tr>
          <tr>
            <td align="right" class="index_w12">密&nbsp;&nbsp;码：</td>
            <td><input type="password" name="pwd" size="18" onkeydown="keyEnter();" /></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td align="center"><a onclick="frmChk();" onkeydown="keyEnter();"><img src="<%=skinPath%>/cssimg/index_an.gif" width="74" height="31" border="0" /></a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    </tr>
    
    <tr><td class="index_bgfoot">长三角商品交易所(模拟)</td></tr>
    <tr><td class="index_bgfoot2">&nbsp;</td></tr> 
</table>
</form>
    <div class="clear"> </div>
    </body>
<SCRIPT LANGUAGE="JavaScript">
<!--
var count=0;
function frmChk()
{
	count++;
	var canSubmit = true;
	if(count!=1){
		canSubmit = false;
	}
	if(frm.username.value == "")
	{
		alert("请填写用户名！");
		canSubmit = false;
		frm.username.focus();
	}
	else if(frm.pwd.value == "")
	{
		alert("请填写密码！");
		canSubmit = false;
		frm.pwd.focus();
	}
	if(canSubmit)
	{
		//frm.logon.value="true";
		frm.submit();	
	}
}
</SCRIPT>