<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<html>
  <title>修改用户口令</title>
<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
<script language="javascript" src="<%=serverPath %>/public/jslib/passwordStrength.js"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=commonUserControllerPath%>commonUserModPassword&sign=old">
		<fieldset width="100%">
		<legend>修改用户口令</legend>
		<BR>
		<span>		
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
        	<td align="right"> 用户代码 ：</td>
            <td align="left">
            	<input name="userId" type="text" class="text" style="width: 180px;" value="${modUser.userId }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> 用户名称 ：</td>
            <td align="left">
            	<input name="name" type="text" class="text" style="width: 180px;" value="${modUser.name }" readonly="readonly">
            </td>
        </tr>
   
        <tr height="35">
            <td align="right"> 原密码 ：</td>
            <td align="left">
            	<input name="oldpass" type="password" class="text" style="width: 180px;">
            </td>
        </tr>
   
        <tr height="35">
            <td align="right"> 新密码 ：</td>
            <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" onKeyUp="passwordStrength(this.value)" onblur="passwordYin(this.value)" onkeypress="notSpace()">
                  <div id="passwordPrompt">
                    <div style="width:70px; float:left;">密码强度：</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                  </div>
                  <div id="msg"></div>
            </td>
        </tr>
        <tr height="35">       
	          <td align="right"> 确认密码 ：</td>
	          <td align="left">
	          	<input name="password1" type="password" class="text" style="width: 180px;" onkeypress="notSpace()">
	          </td>
        </tr>
  	</table>
		<BR>
        </span>
		</fieldset>
		<br>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%"><div align="center">
			  <input type="button" name="btn" onclick="return frmChk()" class="btn" value="保存">&nbsp;&nbsp;
			  <input name="back" type="button" onclick="window.close();" class="btn" value="关闭">&nbsp;&nbsp;
            </div></td>
          </tr>
     </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{ 
	if(Trim(frm.oldpass.value) == "")
	{
		alert("原密码不能为空！");
     	frm.oldpass.focus();
		return false;
	} 
	if(Trim(frm.password.value) == "")
	{
		alert("新密码不能为空！");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(Trim(frm.password.value),8)){
		alert("密码长度不能少于8位，可包含字母、数字、特殊符号,请重新输入！");
		frm.password.focus();
		return false;
	}
	else if(Trim(frm.password1.value) == "")
	{
		alert("确认密码不能为空！");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("新密码与确认密码不一致！");
		frm.password1.focus();
		return false;
	}
	else 
	{ 
	  if(userConfirm()){
		frm.submit();
		//return true;
	   }else{
	    return false;
	   }
	}
}
//-->
</SCRIPT>