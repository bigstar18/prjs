<%@ page contentType="text/html;charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<%
	if(sessinUser != null){//sessinUser 变量在 path.jsp中定义的
		try{
			gnnt.MEBS.common.mgr.common.ActiveUserManager.logoff(request);
		}catch(Exception e){}
	}
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<title>登录页面</title>
		<style type="text/css">
<!--
body { margin:0px auto; padding:150px 0 0 0;}
-->
</style>
	</head>
	<body>
		<div class="login">
			<div class="left">
				<div class="logo1">
					<img src="${skinPath}/image/logon/logo.gif" />
				</div>
				<div class="center_bg">
					<img src="${skinPath}/image/logon/bt.png" />
				</div>
				<div class="footer">
					<div class="l_bt">
						版权所有：
					</div>
					<div class="r_bg">
						<%=Global.getMarketInfoMap().get("copyrightof") %>
						<%if("Y".equalsIgnoreCase(Global.getMarketInfoMap().get("mgrIsNeedKey")) 
								&& Global.getMarketInfoMap().get("marketNO") != null 
									&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){
						%>
						&nbsp;<a href='${basePath}/InstallOCX.zip'><font color='#FF0000'>点此下载 UKey 控件</font></a>
						<%}%>
					</div>

				</div>
			</div>
			<div class="right">
			<form name="frm" method="post" onsubmit="check()"
						action="<%=basePath%>/user/logon.action">
				<table width="193" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="27" align="right" class="font_12b">
							用户名：
						</td>
						<td height="27">
							<label>
								<input type="text" id="userId" name="entity.userId"
										value="${entity.userId }" onkeydown="keyEnter();" size="15"/>
							</label>
						</td>
					</tr>
					<tr>
						<td height="27" align="right" class="font_12b">
							密码：
						</td>
						<td height="27">
							<label>
							<input type="password" name="entity.password" id="password"
										onkeydown="keyEnter();" value="${entity.password }" size="15"/><div class="font_12b" style="display:none;">大写锁定已打开</div>
							</label>
						</td>
						<script type="text/javascript">
							//检测大写锁定键
							function  detectCapsLock(event){
							    var e = event||window.event;
							    var o = e.target||e.srcElement;
							    var oTip = o.nextSibling;
							    var keyCode  =  e.keyCode||e.which; // 按键的keyCode 
							    var isShift  =  e.shiftKey ||(keyCode  ==   16 ) || false ; // shift键是否按住
							     if (
							     ((keyCode >=   65   &&  keyCode  <=   90 )  &&  !isShift ) // Caps Lock 打开，且没有按住shift键 
							     || ((keyCode >=   97   &&  keyCode  <=   122 && isShift ) )// Caps Lock 没有打开，且按住shift键
							     ){oTip.style.display = '';}
							     else{oTip.style.display  =  'none';} 
							}
							document.getElementById('password').onkeypress = detectCapsLock;
					</script> 
						
					</tr>
					<tr>
						<td height="27" align="right" class="font_12b">
							验证码：
						</td>
						<td height="27">
							<label>
								<input style="margin-bottom: 5px;" type="text" name="randNumInput" 
										onkeydown="keyEnter();" size="6"/>
									<img src="<%=publicPath%>/jsp/logoncheckimage.jsp?,Math.random();"
										id="imgCode" style="padding-top: 5px;"><br/>
										<a href="javascript:changeimgCode();" style="font-size: 12px;">看不清,换一张</a>
							</label>
						</td>
					</tr>
					<tr>
						<td height="40" colspan="2" align="left" valign="middle">
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="check();" onkeydown="keyEnter();"><img src="${skinPath}/image/logon/login.gif" border="0" /> </a>&nbsp;&nbsp;
							<a href="#" onclick="myReset();"><img src="${skinPath}/image/logon/cz.gif" border="0" /> </a>
						</td>
					</tr>
				</table></form>
			</div>
		</div>
	</body>
</html>
<%if("Y".equalsIgnoreCase(Global.getMarketInfoMap().get("mgrIsNeedKey")) 
				&& Global.getMarketInfoMap().get("marketNO") != null 
				&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){%>
<script type="text/javascript" src="${basePath}/mgr/public/js/keycode.js"></script>
<%}%>
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
	//document.getElementById("IEUse").innerHTML=IeMsg;
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
	if(document.getElementById("userId").value == "")
	{
		alert("请填写用户名！");
		 submit=0;
		canSubmit = false;
		document.getElementById("userId").focus();
	}
	else if(document.getElementById("password").value == "")
	{
		alert("请填写密码！");
		 submit=0;
		canSubmit = false;
		document.getElementById("password").focus();
	}
	/**
	else if(frm.randNumInput.value == "")
	{
		alert("请填写验证码！");
		 submit=0;
		canSubmit = false;
		frm.randNumInput.focus();
	}*/
	else{
        document.getElementById("userId").value=document.getElementById("userId").value.replace(/(^\s*)|(\s*$)/g,"");
	}
	if(canSubmit)
	{
		//frm.logon.value="true";
		<%if("Y".equalsIgnoreCase(Global.getMarketInfoMap().get("mgrIsNeedKey")) 
				&& Global.getMarketInfoMap().get("marketNO") != null 
				&& !"0".equals(Global.getMarketInfoMap().get("marketNO"))){%>
		var m = checkKeyCode('<%=Global.getMarketInfoMap().get("marketNO") %>',document.getElementById("userId").value,frm);
		if(!m.passed){
			alert(m.msg);
			submit=0;
			return;
		}
		<%}%>
		frm.submit();
	}
}
function keyEnter()
{
	if ( event.keyCode == 13 )
	{
		check();
	}
}
if(document.getElementById("userId").value==''){
	document.getElementById("userId").focus();
} else {
	document.getElementById("password").focus();
}

function changeimgCode(){
	var imgCode=document.getElementById('imgCode');
	imgCode.src="<%=publicPath%>/jsp/logoncheckimage.jsp?" + Math.random();
}
//-->
</SCRIPT>
