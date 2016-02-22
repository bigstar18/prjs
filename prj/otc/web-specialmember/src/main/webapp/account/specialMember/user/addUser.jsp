<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="/common/public/common.jsp"%>
<body style="overflow-y:hidden">
<OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
        <form name="frm" id="frm" method="post" targetType="hidden">
		<div class="div_scromin">
			<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;添加特别会员管理员</div>
			<table border="0" width="500" align="center">
				<tr height="100"></tr>
				<tr>
					<td>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
			  <tr height="35">
            	<td align="right"> 用户代码 ：</td>
                <td align="left">
                	<input id="userId" class="input_text" name="obj.userId" onkeyup="value=value.replace(/[\W]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" type="text" class="text"><b><font color=red>&nbsp;*</font></b>
                </td>
        </tr>
        <tr height="35">
            	<td align="right"> 姓名 ：</td>
                <td align="left">
                	<input id="name" class="input_text" name="obj.name" type="text" class="text">
                </td>
			  </tr>
        <tr height="35">
                <td align="right"> 密码 ：</td>
                <td align="left">
                	<input id="password" class="input_text" name="obj.password" type="password" class="text"><b><font color=red>&nbsp;*</font></b>
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 确认密码 ：</td>
                <td align="left">
                	<input id="password1" class="input_text" name="password1" type="password" class="text"><b><font color=red>&nbsp;*</font></b>
                </td>
              </tr>
              <input type="hidden" name="enableKey" id="enableKey" value="N">
         <!-- 
         <tr height="35">
            	<td align="right"> 是否启用key ：</td>
                <td align="left">
                	<input type="radio" id="enableKey" name="obj.enableKey" value="Y" onClick="onSelect(this.value)">启用
                	<input type="radio" id="enableKey" name="obj.enableKey" value="N" checked="checked" onClick="onSelect(this.value)">不启用
                </td>
         </tr>
			<tr id="showTr" style="display: none">
            	<td align="right"> key ：</td>
                <td align="left">
                    <input ytpe="text" id="keyCode" name="obj.keyCode" style="width: 150px;">
                    <button class="smlbtn" id="key" name="obj.key" type="button" onclick="addKey()">绑定key</button>
                </td>
              </tr>
             -->
              <tr height="35">
                <td align="right"> 用户描述 ：</td>
                <td align="left">
                	<textarea id="description" name="obj.description" class="normal"></textarea>
                </td>
               </tr>
        	</table>
        	</td>
        	</tr>
        	</table>
        	</div>
		 <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="tab_pad">  
	 <tr>
		<td align="center">
			<button  class="btn_sec" onClick="frmChk()" id="addUser">添加</button>
		</td>
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">关闭</button>
		</td>
	</tr>
		 </table>
		 
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	if(Trim(frm.userId.value) == "" || Trim(frm.userId.value).toUpperCase() == "SYSTEM")
	{
		alert("用户id不能为空或已被使用！");
		frm.userId.value="";
		frm.userId.focus();
		return false;
	}
	if(Trim(frm.password.value) == "")
	{
		alert("密码不能为空！");
		frm.password.focus();
		return false;
	}
	if(Trim(frm.password1.value) == "")
	{
		alert("确认密码不能为空！");
		frm.password1.focus();
		return false;
	}
	if(!chkLen(Trim(frm.password.value),6))
	{
		alert("密码不能小于6位,建议使用8位以上,不能以空格作为密码,请重新输入！");
		frm.password.focus();
		return false;
	}
	if(!chkLen(Trim(frm.password1.value),6))
	{
		alert("密码确认不能小于6位,建议使用议8位以上,不能以空格作为密码,请重新输入！");
		frm.password1.focus();
		return false;
	}
	if(frm.password1.value != frm.password.value)
	{
		alert("两次输入的密码不一致！");
		frm.password.focus();
		return false;
	}
//	if(frm.enableKey[0].checked && frm.keyCode.value == '')
	//{
		//alert("请输入key值！");
		//frm.keyCode.focus();
		//return false;
	//}
	//if(!frm.enableKey[0].checked)
	//{
	//	frm.keyCode.value = "0123456789ABCDE";
	//}

	var useridlen = getLength(frm.userId.value);
	var passwordlen = getLength(frm.password.value);
	var usernamelen = getLength(frm.name.value);
	var descriptionlen = getLength(frm.description.value);
	var mark = true;
	var sign = false;
	if(useridlen>32 || passwordlen>128 || usernamelen>32 || descriptionlen>64){
		mark = false;
		alert("您提交的数据长度过大。\n请保证用户id和用户姓名长度不超过32，密码长度不超过128，备注长度不超过64。");
	}
	
	if(mark){		
	  if(userConfirm()){
	  	sign = true;
		//return true;
	  }else{
	    return false;
	  }
	}
	//防止重复提交
	if(sign){
		frm.action="<%=basePath %>/account/specialMemberUser/add.action";
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
    
    //设置key选项 showTr
   function onSelect(value)
		{
		   if(value=='Y')
		   {
		   		document.getElementById("showTr").style.display='';
		   }
		   else
		   {
		     document.getElementById("showTr").style.display='none';
		   }
		}
		
function addKey()
{
	 var str1 = "";
	 var errorCode = 0;
	 var ifInstalled = true;
	 var traderId=frm.userid.value;
	try
	{
		str1 = ePass.VerifyUser(<%=marketId%>,traderId);	
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
		}else if (errorCode==-1)
		{
			alert("请插入USB身份验证盘！");	
		}else if (errorCode==-2)
		{
			alert("非法USB身份验证盘！");	
		}else if (errorCode==-3)
		{
			alert("USB身份验证盘不正确");	
		}else if (errorCode==-4)
		{
			alert("USB身份验证盘已经损坏，请联系发放者！");	
		}
	}
	
	if(!ifInstalled)
	{
		alert("请安装交易控件以正常绑定！");	
		return false;	
	}
	else
	{	   
		if(errorCode==0)
		{
			frm.keyCode.value = str1;	
		}else
		{
			return false;	
		}			
	}
}
//-->
</SCRIPT>
<%@ include file="/public/footInc.jsp"%>