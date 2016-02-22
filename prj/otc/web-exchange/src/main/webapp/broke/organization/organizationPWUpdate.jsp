<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/broke/organization/updatePassword.action" targetType="hidden">
		<div class="div_scromin">
			<table border="0" height="300" width="90%" align="center" >
				<tr height="100"></tr>
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;修改机构密码</div>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
        	<td align="right"> 机构代码 ：</td>
            <td align="left">
            	<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
            	
            	<input name="obj.organizationNO" type="text" class="input_text_mid" value="${obj.organizationNO }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right"> 机构名称 ：</td>
            <td align="left">
            	<input name="obj.name" type="text" class="input_text_mid" value="${obj.name }" readonly="readonly">
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
            <td align="right"> 新电话密码 ：</td>
            <td align="left">
            	<input id="password" name="obj.password" type="password" class="input_text_mid">
            </td>
        </tr>
        <tr height="35">       
	          <td align="right"> 新电话密码确认 ：</td>
	          <td align="left">
	          	<input name="password1" type="password" class="input_text_mid">
	          </td>
        </tr>
  	</table>
  	</td>
  	</tr>
  	</table>
  	</div>
  	<div class="tab_pad">
      <table border="0" cellspacing="0" cellpadding="0" width="90%" >
		  <tr height="35">
			<td width="30%"><div align="center">
				<button  class="btn_sec" onClick="frmChk()" id="update">保存</button>
           </td>
			<td width="30%"><div align="center">
				<button  class="btn_sec" onClick="window.close()">关闭</button></td>
		  </tr>
	 </table>
	 </div>
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
		alert("电话密码不能为空！");
		frm.password.focus();
		return false;
	}
	else if(Trim(frm.password1.value) == "")
	{
		alert("确认电话密码不能为空！");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("电话密码与确认电话密码不一致！");
		frm.password.focus();
		return false;
	}else if(!chkLen(Trim(frm.password1.value),6)){
		alert("电话密码确认不能小于6位,建议使用议8位以上,不能以空格作为密码,请重新输入！");
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