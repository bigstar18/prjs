<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<base target="_self">
<c:if test="${not empty modSuccess }">
	<SCRIPT LANGUAGE="JavaScript">
		window.returnValue="1";
		window.close();
	</SCRIPT>
</c:if>
<body>
<form name="frm" id="frm" method="post" action="<%=commonRoleControllerPath %>commonRoleModForward">
		<fieldset width="100%">
		<legend>修改系统角色</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
			  <tr height="35">
            	<td align="right"> 角色代码 ：</td>
                <td align="left">
                	${role.id }<input type="hidden" name="id" value="${role.id }">
                </td>
        </tr>        
	  <tr height="35">
		<td align="right"> 角色姓名 ：</td>
		<td align="left">
			<input name="name" type="text" class="input_text_mid" value="${role.name }">
		</td>
	  </tr>
	  <tr height="35">
		<td align="right"> 角色描述 ：</td>
		<td align="left">
			<textarea name="description" class="normal">${role.description }</textarea>
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
		var rolenamelen = getLength(frm.name.value);
		var descriptionlen = getLength(frm.description.value);
		var mark = true;
		var sign = false;
		if(rolenamelen>32 || descriptionlen>64){
			mark = false;
			alert("您提交的数据长度过大。\n请保证角色名长度不超过32，角色描述长度不超过64。");
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

//求混合字符串长度
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