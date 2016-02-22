<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/account/memberUser/updatePassword.action" targetType="hidden">
		<fieldset width="100%">
		<legend>修改用户密码</legend>
		<BR>
		<span>		
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
        	<td align="right"> 用户代码 ：</td>
            <td align="left">
            	<input name="obj.userId" type="text" class="input_text_mid" value="${modUser.userId }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> 用户名称 ：</td>
            <td align="left">
            	<input name="name" type="text" class="input_text_mid" value="${modUser.name }" readonly="readonly">
            </td>
        </tr>
        <!-- 
        <tr height="35">
            <td align="right"> 原密码 ：</td>
            <td align="left">
            	<input name="oldpass" type="password" class="text" style="width: 180px;">
            </td>
        </tr>
         -->
        <tr height="35">
            <td align="right"> 新密码 ：</td>
            <td align="left">
            	<input id="password" name="obj.password" type="password" class="input_text_mid">
            </td>
        </tr>
        <tr height="35">       
	          <td align="right"> 新确认密码 ：</td>
	          <td align="left">
	          	<input name="password1" type="password" class="input_text_mid">
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
          		<button name="btn" onclick="frmChk()" id="updatePassword">保存</button>
          		<button onclick="window.close()">关闭</button>
            </div></td>
          </tr>
     </table>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{ 
	//if(Trim(frm.oldpass.value) == "")
	//{
	//	alert("原密码不能为空！");
	//	frm.oldpass.focus();
	//	return false;
	//}
	//else 
	if(Trim(frm.password.value) == "")
	{
		alert("密码不能为空！");
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
		alert("密码与确认密码不一致！");
		frm.password.focus();
		return false;
	}else if(!chkLen(Trim(frm.password1.value),6)){
		alert("密码确认不能小于6位,建议使用议8位以上,不能以空格作为密码,请重新输入！");
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
<%@ include file="/public/footInc.jsp"%>