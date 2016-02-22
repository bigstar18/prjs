<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/public/commonLogon.jsp" %>
<%
	session.removeAttribute("resultMsg");
	session.removeAttribute("resultValue");
	session.removeAttribute("invalidationSign");
	Cookie cookies[]=request.getCookies();   
	Cookie sCookie=null;   
	String svalue=null;   
	String sname=null;
	String userName="";
	if(cookies!=null)
	{
		for(int   i=0;i<cookies.length;i++){   
			sCookie=cookies[i];   
			svalue=sCookie.getValue();   
			sname=sCookie.getName();
			//out.println("sname:"+sname+"  svalue:"+svalue);
		    if(sname.equals("gnnt_username"))
	    	{
		    	userName=svalue;
	    	}
	    }
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="favicon.ico">
<link rel="Bookmark" href="favicon.ico">	
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<script type="text/javascript" src="<%=publicPath%>/md5.js"></script>
<title>长三角商品交易所(模拟)</title>
</head>
<body class="login_body">
<table width="772" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="160" colspan="2"></td>
  </tr>
  <tr>
	<td  colspan="2" align="right"><div id="useIE"><span style="color: red;font-size: 35px " id="IEUse"></span></div></td>
  </tr>
  <tr>
    <td width="427">
      <div class="login_logo"></div>
      <div class="login_pic"></div>
    </td>
    <td valign="top">
	<form name="frm" onsubmit="check();" method="post" action="<%=basePath %>/user/logon.action">
	<table width="350" border="0" align="left" cellpadding="0" cellspacing="0">
          <tr>
            <td height="50" colspan="3" class="login_topbg"></td>
          </tr>
           <tr>
            <td align="left" class="logo_midbgl" style="border-right:1px solid #cfcfcf;" colspan="3"><img src="<%=skinPath%>/cssimg/login_user.gif" /></td>
          </tr>
          <tr>
            <td width="25%" align="right" class="logo_midbgl">用户名：</td>
            <td class="logo_midbgr" colspan="2"><input type="text" name="username" value="<%=userName%>" style="width: 140px;" onkeydown="keyEnter();" />            </td>
          </tr>
          <tr>
            <td align="right" class="logo_midbgl">密&nbsp;&nbsp;码：</td>
            <td class="logo_midbgr" colspan="2">
            	<input type="password" name="pwd1" style="width: 140px;" onkeydown="keyEnter();" />
            	<input type="hidden" name="pwd" />
            </td>
          </tr>
          <tr>
            <td align="right" class="logo_midbgl" width="25%">验证码：</td>
            <td class="logo_midbgr1" width="45%"><input type="text"  name="randNumInput" style="width: 60px;" onkeydown="keyEnter();" />&nbsp;<img src="<%=serverPath %>/public/image.jsp?,Math.random();" id="imgCode" align="absmiddle"></td>
            <td class="logo_midbgr" align="left"><a href="javascript:changeimgCode();" style="font-size: 12px;">看不清,换一张</a></td>
          </tr>
          <tr>
            <td class="logo_midbgl">&nbsp;</td>
            <td align="center" valign="middle" class="logo_midbgr" colspan="2"><a onclick="check();" onkeydown="keyEnter();"><img src="<%=skinPath%>/cssimg/index_an.gif" border="0" align="absmiddle" /></a></td>
          </tr>
          <tr>
            <td colspan="3" height="1" bgcolor="#d1cfc7"></td>
          </tr>
          <tr>
            <td class="logo_midbgl">&nbsp;</td>
            <td align="right" valign="middle" class="logo_midbgr" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;</td>
          </tr>
          <tr>
            <td colspan="3" class="login_dowbg"></td>
          </tr>
        </table>
		</form>
		</td>
  </tr>
  <tr>
    <td colspan="3" align="center" class="login_w14">长三角商品交易所(模拟)</td>
  </tr>
</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
var IeMsg="请使用ie8或ie9浏览器";
var flag = true;
if(navigator.userAgent.indexOf("MSIE")>0)
{ 
	if(navigator.userAgent.indexOf("MSIE 6.0")>0)
	{ 
	flag = false;
	} 
	if(navigator.userAgent.indexOf("MSIE 7.0")>0)
	{
	flag = false;
	} 
	if(navigator.userAgent.indexOf("MSIE 8.0")>0)
	{
//	alert("ie8");
	} 
	if(navigator.userAgent.indexOf("MSIE 9.0")>0)
	{
	//alert("ie9");
	} 
}else
{
flag = false;
} 
if(!flag){
	document.getElementById("IEUse").innerHTML=IeMsg;
}

 function addCookie(objName,objValue,objHours){//添加cookie
			 var str = objName + "=" + escape(objValue);
			 if(objHours > 0){//为0时不设定过期时间，浏览器关闭时cookie自动消失
			 var date = new Date();
			 var ms = objHours*3600*1000;
			 date.setTime(date.getTime() + ms);
			 str += "; expires=" + date.toGMTString()+";path=<%=request.getContextPath()%>";
			 }
			 document.cookie = str;
			 }

 var submit=0;    
 function check(){    
      if(++submit>1)
    	  {
    	  return false;    
    	  }
      else{
    	  frmChk();
           return true;    
              }
      }
 
function frmChk()
{
	var canSubmit = true;
	if(frm.username.value == "")
	{
		alert("请填写用户名！");
		 submit=0;
		canSubmit = false;
		frm.username.focus();
	}
	else if(frm.pwd1.value == "")
	{
		alert("请填写密码！");
		 submit=0;
		canSubmit = false;
		frm.pwd1.focus();
	}
	else if(frm.randNumInput.value == "")
	{
		alert("请填写验证码！");
		 submit=0;
		canSubmit = false;
		frm.randNumInput.focus();
	}else{
        frm.username.value=frm.username.value.replace(/(^\s*)|(\s*$)/g,"");
	}
	frm.pwd.value = hex_md5(frm.username.value+frm.pwd1.value);
	if(canSubmit)
	{
		frm.pwd1.disabled = "true";
		addCookie("gnnt_username",frm.username.value,24*365);
		frm.submit();
		frm.pwd1.disabled = "";
	}
}
function keyEnter()
{
	if ( event.keyCode == 13 )
	{
		check();
	}
}

if(frm.username.value==''){
	frm.username.focus();
} else {
	frm.pwd1.focus();
}
function clean()
{
	frm.username.value = "";
	frm.pwd1.value = "";
	frm.randNumInput.value = "";
}
function changeimgCode(){
	var imgCode=document.getElementById('imgCode');
	imgCode.src="<%=serverPath %>/public/image.jsp?" + Math.random();
}
function CookieEnable()
 {
  var result=false;
  var cookieSet=document.cookie;
  if(cookieSet.indexOf("JSESSIONID")>-1)
   result=true;
  return result;
 }
 if(!CookieEnable()){
  alert("对不起，您的浏览器的Cookie功能被禁用，请加入可信任站点或者将cookie级别调制中高级以下");
 }
//-->
</SCRIPT>