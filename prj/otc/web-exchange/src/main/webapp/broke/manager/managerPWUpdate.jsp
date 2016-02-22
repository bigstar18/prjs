<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body>
<form name="frm" id="frm" method ="post" action="<%=basePath%>/broke/manager/updatePassword.action" targetType="hidden">
		<div class="div_scromin">
			<table border="0" height="300" width="90%" align="center" >
				<tr height="100"></tr>
				<tr>
				 <td>
				 <div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;修改经纪人密码</div>	
			<table border="0" cellspacing="0" cellpadding="0" width="100%" class="st_bor">
		<tr height="35">
        	<td align="right">客户代表代码 ：</td>
            <td align="left">
            	<input type="hidden" name="obj.memberNo" value="${REGISTERID }">
            	<input type="hidden" name="obj.parentOrganizationNO" value="${obj.parentOrganizationNO}">
            	<input type="hidden" name="obj.email" value="${obj.email }">
            	<input type="hidden" name="obj.telephone" value="${obj.telephone }">
            	<input type="hidden" name="obj.mobile" value="${obj.mobile }">
            	<input type="hidden" name="obj.address" value="${obj.address}">
            	<input name="obj.managerNo" type="text" class="input_text_mid" value="${obj.managerNo }" readonly="readonly">
            </td>
        </tr>
        <tr height="35">
        	<td align="right">客户代表名称 ：</td>
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
  	</td>
  	</tr>
  	</table>
  	</div>
  	<div class="tab_pad">
      <table border="0" cellspacing="0" cellpadding="0" width="90%">
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