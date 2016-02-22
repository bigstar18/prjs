<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<base target="_self">
<c:if test="${not empty addSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<body>
<form name="frm" id="frm" action="<%=commonRoleControllerPath %>commonRoleAddForward" method="post" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>添加系统角色</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="80%" align="center">
			  <tr height="35">
            	<td align="right"> 角色代码 ：</td>
                <td align="left">
                	<input name="id" type="text" class="input_text_mid"><b><font color=red>&nbsp;*请输入数字</font></b>
                </td>
        </tr>
			  <tr height="35">
            	<td align="right"> 角色名称 ：</td>
                <td align="left">
                	<input name="name" type="text" class="input_text_mid">
                </td>
			  </tr>
              <tr height="35">
                <td align="right"> 角色描述 ：</td>
                <td align="left">
                	<textarea name="description" class="normal"></textarea>
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
				  <input name="back" type="button" onclick="javaScript:window.close();" class="btn" value="关闭">&nbsp;&nbsp;
				</div></td>
			  </tr>
		 </table>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	if(Trim(frm.id.value) == "")
	{
		alert("角色代码不能为空！");
		frm.id.focus();
		return false;
	}
	else if(isNaN(Trim(frm.id.value)))
	{
		alert("角色代码须为数字！");
		frm.id.focus();
		return false;
	}
	else 
	{
		var roleidlen = getLength(frm.id.value);
		var rolenamelen = getLength(frm.name.value);
		var descriptionlen = getLength(frm.description.value);
		var mark = true;
		var sign = false;
		
		if(roleidlen>32 || rolenamelen>32 || descriptionlen>64){
			mark = false;
			alert("您提交的数据长度过大。\n请保证角色id和角色名长度不超过32，角色描述长度不超过64。");
		}
		if(mark){		
			  if(userConfirm()){
				frm.btn.disabled=true;
				sign = true;
			  }else{
			    return false;
			  }
		}
	//防止重复提交
	if(sign){
			frm.submit();
		}
	}
}

function getLength(v){
	
	var vlen = 0;
	var str = v.split("");
	for( var a=0 ; a<str.length ; a++)
	{
		if (str[a].charCodeAt(0)<299){ 
			vlen++;
		}else{
			vlen+=2;
		}
	}
	return vlen;
}
//-->
</SCRIPT>