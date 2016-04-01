<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../public/common.jsp"%>
<c:if test="${not empty addSuccess }">
	<script>
		window.returnValue="1";
		window.close();
	</script>
</c:if>
<html>
  <title>添加系统用户</title>
    <link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
    <script language="javascript" src="<%=serverPath %>/public/jslib/passwordStrength.js"></script>
  <base target="_self">

<body>
<!-- <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT> -->
<form name="frm" id="frm" method="post">
		<fieldset width="100%">
		<legend>添加系统用户</legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
			  <tr height="35">
            	<td align="right"> 用户代码 ：</td>
                <td align="left">
                	<input name="userId" onkeyup="value=value.replace(/[\W]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" type="text" class="text" style="width: 180px;" onkeypress=" return onlyNumberAndCharInput()" maxlength="16"><b><font color=red>&nbsp;*</font></b>
                </td>
        </tr>
        <tr height="35">
            	<td align="right"> 用户姓名 ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 180px;" onkeypress="return onlyNumberAndCharInput()" maxlength="16">
                </td>
			  </tr>
        <tr height="35">
                <td align="right"> 密码 ：</td>
                <td>
                  <input name="password" type="password" class="text" style="width: 180px;" onKeyUp="passwordStrength(this.value)" onblur="passwordYin(this.value)" maxlength="16" onkeypress="notSpace()"><b><font color=red>&nbsp;*</font></b>
                  <div id="passwordPrompt">
                    <div style="width:70px; float:left;">密码强度：</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                  </div>
                  <div id="msg"></div>
                </td>
                <td align="left">
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 确认密码 ：</td>
                <td align="left">
                	<input name="password1" type="password" class="text" style="width: 180px;" onkeypress="notSpace()" maxlength="16"><b><font color=red>&nbsp;*</font></b>
                </td>
              </tr>
         <tr height="35">
            	<td align="right"> 是否启用key ：</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)">启用
                	<input type="radio" name="enableKey" value="N" checked="checked" onClick="onSelect(this.value)">不启用
                </td>
         </tr>
			<tr id="showTr" style="display: none">
            	<td align="right"> key ：</td>
                <td align="left">
                    <input ytpe="text" name="keyCode" style="width: 150px;" onblur="checkKey()">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">绑定key</button>
                </td>
              </tr>
			  <input type="hidden" name="skinstyle" value="default"/>
			  <!--
			  <tr height="35">
            	<td align="right"> 选择风格 ：</td>
                <td align="left">
                	<select name="skin">
					<option value="">请选择</option>
					<c:forEach items="${skinMap }" var="snMap">
						<option value="${snMap.key }">${snMap.value }</option>
					</c:forEach>
				</select>               	
                </td>
			  </tr>
			  -->
              <tr height="35">
                <td align="right"> 用户描述 ：</td>
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
				  <input type="button" name="btn" onclick="frmChk()" class="btn" value="保存">&nbsp;&nbsp;
				  <input name="back" type="button" onclick="javaScript:window.close();" class="btn" value="关闭">&nbsp;&nbsp;
				</div></td>
			  </tr>
		 </table>
		 
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
  function checkKey(){
	  if(frm.enableKey[0].checked && frm.keyCode.value == '0123456789ABCDE')
		{
			alert("key值重复，请重新输入！");
			frm.keyCode.value = "";
			frm.keyCode.focus();
			return;
		}
  }

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
	if(!chkLen(Trim(frm.password.value),8))
	{
		alert("密码长度不能少于8位，可包含字母、数字、特殊符号,请重新输入！");
		frm.password.focus();
		return false;
	}
	if(Trim(frm.password1.value) == "")
	{
		alert("确认密码不能为空！");
		frm.password1.focus();
		return false;
	}
	if(frm.password1.value != frm.password.value)
	{
		alert("两次输入的密码不一致！");
		frm.password1.focus();
		return false;
	}
	if(frm.enableKey[0].checked && frm.keyCode.value == '')
	{
		alert("请输入key值！");
		frm.keyCode.focus();
		return false;
	}
	
	if(!frm.enableKey[0].checked)
	{
		frm.keyCode.value = "0123456789ABCDE";
	}
	

	var useridlen = getLength(frm.userId.value);
	var passwordlen = getLength(frm.password.value);
	var usernamelen = getLength(frm.name.value);
	var descriptionlen = getLength(frm.description.value);
	var mark = true;
	var sign = false;
	
	if(useridlen>32){
		mark = false;
		alert("您提交的数据长度过大。\n请保证用户代码长度不超过16。");
		frm.userId.focus();
		return false;
	}
	if(usernamelen>32){
		mark = false;
		alert("您提交的数据长度过大。\n请保证用户姓名长度不超过32。");
		frm.name.focus();
		return false;
	}
	//if(passwordlen>128){
	//	mark = false;
	//	alert("您提交的数据长度过大。\n请保证密码长度不超过128。");
	//	return false;
	//}
	if(descriptionlen>512){
		mark = false;
		alert("您提交的数据长度过大。\n请保证描述长度不超过512。");
		frm.description.focus();
		return false;
	}
	if(mark){		
	  if(userConfirm()){
	  	frm.btn.disabled=true;
	  	sign = true;
		//return true;
	  }else{
	    return false;
	  }
	}
	//防止重复提交
	if(sign){
		frm.action="<%=commonUserControllerPath %>commonUserAddForward";
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
	 var traderId=frm.userId.value;
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
//仅输入数字和字母
function onlyNumberAndCharInput()
{
  if ((event.keyCode>=48 && event.keyCode<=57) || (event.keyCode>=65 && event.keyCode<=90) || (event.keyCode>=97 && event.keyCode<=122))
  {
    event.returnValue=true;
  }
  else
  {
    event.returnValue=false;
  }
}
//-->
</SCRIPT>