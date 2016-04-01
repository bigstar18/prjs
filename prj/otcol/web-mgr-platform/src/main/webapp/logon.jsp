<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/public/common.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<title>资金统一结算系统</title>
</head>
<!-- <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
      STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
      CODEBASE="GnntKey.cab#Version=1,0,0,5">
</OBJECT> -->
<style>
<!--
body
{
	background-color: #0a7ec6;
	margin: 0px;
}
.t_bg01
{
	background-image: url(<%=serverPath %>/public/menuicon/bg.gif);
	background-repeat: repeat-x;
}
.t_bgq
{
	background-image: url(<%=serverPath %>/public/menuicon/bg_q.gif);
	background-repeat: no-repeat;
	background-position: center top;
}
.title
{
	font-family: "楷体_GBK";
	font-size: 30px;
	color: #f0f9fb;
	float: left;
	margin-top: 130px;
	margin-left: 45px;
	vertical-align: top;
	text-align: left;
}
.text
{
	font-size: 14px;
	line-height: 25px;
	font-weight: normal;
	color: #FFFFFF;
}
.bor
{
	font-size: 12px;
	border: 1px solid #1a64b1;
	width: 140px;
}
.bor_rand
{
	font-size: 12px;
	border: 1px solid #1a64b1;
	width: 70px;
}
-->
</style>
<body>
	<form name="frm" method="post" action="<%=commonUserControllerPath %>commonUserLogon">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" >
		  <tr>
		    <td align="center" class="t_bg01"><table width="904" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td align="left" valign="top" class="t_bgq">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td><div class="title">资金统一结算系统</div></td>
		  </tr>
		  <tr>
		    <td height="100">&nbsp;</td>
		  </tr>
		  <tr>
		    <td align="right"><table width="40%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td align="right" class="text">用户名：</td>
		        <td align="left"><input type="text" name="username"  class="bor" value="${user.userId }" onkeydown="keyEnter();"/></td>
		      </tr>
		      <tr>
		        <td align="right" class="text">密&nbsp;&nbsp;码：</td>
		        <td align="left"><input type="password" name="pwd" class="bor" onkeydown="keyEnter();"/></td>
		      </tr>
		      <tr>
		        <td align="right" class="text">验证码：</td>
		        <td align="left">
		        	<input type="text" name="randNumInput" class="bor_rand" onkeydown="keyEnter();"/>
		        	<img src="<%=serverPath %>/public/image.jsp" align="absmiddle" style="cursor: hand;"  alt="点击图片刷新验证码" onclick="this.src='<%=serverPath %>/public/image.jsp?r='+Math.random()">
		        </td>
		      </tr>
			   <tr>
		        <td colspan="2" height="10"></td>
		      </tr>
		      <tr>
		        <td colspan="2" align="center">
					<a onclick="frmChk();" style="cursor:hand" onkeydown="keyEnter();"><img src="<%=serverPath %>/public/menuicon/bt01.gif" width="64" height="26" border="0" /></a>
					&nbsp;&nbsp;
					<a onclick="clean();" style="cursor:hand" onkeydown="keyEnter();"><img src="<%=serverPath %>/public/menuicon/bt02.gif" width="64" height="26" border="0" /></a>
					<input type="hidden" name="logon">
				</td>
		      </tr>
		    </table></td>
		  </tr>
  <table align="center" valign="center" border="0" cellpadding="0" cellspacing="10">
  	<tr>
  		<td colspan="2" align="left" width="40%">&nbsp;&nbsp;</td>
	     <td colspan="2" align="left">
	     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     	<font style="font-size:12px;font-family:宋书">如果安全控件安装失败，请
	     	<a href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() %>/InstallOCX.zip">点击此处</a>
	     	下载。</font>
	     </td>
     </tr>
     <tr>
     	<td colspan="2" align="left" width="40%">&nbsp;&nbsp;</td>
	     <td colspan="2" align="left">
	     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     	<font style="font-size:12px;font-family:宋书">下载成功后请关闭IE浏览器，然后进行安装。</font>
	     </td>
     </tr>
  </table>
  <input type=hidden name="kcode">
		<tr>
		    <td height="200">&nbsp;</td>
		 </tr>
		</table></td>
		      </tr>
		    </table></td>
		  </tr>
		</table>
	</form>
</body>
<SCRIPT LANGUAGE="JavaScript">

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
	else if(frm.randNumInput.value == "")
	{
		alert("请填写验证码！");
		canSubmit = false;
		frm.randNumInput.focus();
	}
	
	//key盘检验
	var str1 = "";
	var errorCode = 0;
	var ifInstalled = true;
	try
	{
		str1 = ePass.VerifyUser(<%=marketId %>,frm.username.value);
	}
	catch(err)
	{
		ifInstalled = false;													
	}
	if(isNaN(str1))
	{
	}
	else
	{
		ifInstalled = true;
		errorCode = parseInt(str1);
		if(errorCode==-10)
		{
			alert("读Key驱动程序错误！");
			canSubmit = false;
		}
		else if (errorCode==-1)
		{
			//alert("请插入USB身份验证盘！");	
		}
		else if (errorCode==-2)
		{
			alert("非法USB身份验证盘！");
			canSubmit = false;	
		}
		else if (errorCode==-3)
		{
			alert("USB身份验证盘不正确");
			canSubmit = false;	
		}
		else if (errorCode==-4)
		{
			alert("USB身份验证盘已经损坏，请联系发放者！");
			canSubmit = false;	
		}
	}
			
	if(!ifInstalled)
	{
		alert("请安装交易控件以正常登录系统！");
		canSubmit = false;	
		return false;	
	}
	else
	{
		if(str1 == -1)
		{
			frm.kcode.value = "0123456789ABCDE";
		}
		else 
		{
			frm.kcode.value = str1;
		}				
	}
		
	if(canSubmit)
	{
		frm.logon.value="true";
		frm.submit();	
	}
}
function keyEnter()
{
	if ( event.keyCode == 13 )
	{
		frmChk();
	}
}

function clean()
{
	frm.username.value = "";
	frm.pwd.value = "";
	frm.randNumInput.value = "";
}

</SCRIPT>