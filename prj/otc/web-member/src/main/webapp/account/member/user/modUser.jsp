<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body class="st_body">
 <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
     <form name="frm" id="frm" method="post" targetType="hidden">
		<fieldset width="100%">
		<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" width="43" height="40" align="absmiddle" />&nbsp;&nbsp;修改系统用户</div>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="80%" align="center">
		  <tr height="35">
           	<td align="right"> 用户代码 ：</td>
               <td align="left">
               	${obj.userId }
               	<input type="hidden" class="from" id="userid" name="obj.userId" value="${obj.userId}">
               	<input type="hidden" class="from" name="obj.password" value="${obj.password}">
               	<input type="hidden" class="from" name="obj.skin" value="${obj.skin}">
               </td>
         </tr>
         <tr height="35">
			<td align="right"> 用户姓名 ：</td>
			<td align="left">
				<input class="from" name="obj.name" id="name" type="text" class="input_text_mid" value="${obj.name}">
			</td>
		  </tr>
		  <input class="from" type="hidden" name="enableKey" id="enableKey" value="N">
		  <!-- 
		  <c:set var="codeValue" value="0123456789ABCDE"/>
		  
		 <%// if(!"0123456789ABCDE".equals(((User)request.getAttribute("obj")).getKeyCode())){
		  %>
	         <tr height="35">
	            	<td align="right"> 是否启用key ：</td>
	                <td align="left">
	                	<input type="radio" name="enableKey" value="Y" checked="checked" onClick="onSelect(this.value)">启用
	                	<input type="radio" name="enableKey" value="N" onClick="onSelect(this.value)">不启用
	                </td>
	  		   <tr id="showTr" style="">
            	<td align="right"> key ：</td>
                <td align="left">
                    <input type="text" name="keyCode" value="${user.keyCode }" style="width: 150px;">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">绑定key</button>
                </td>
              </tr>
              <%//}else{ %>
         	<tr height="35">
            	<td align="right"> 是否启用key ：</td>
                <td align="left">
                	<input type="radio" name="enableKey" value="Y" onClick="onSelect(this.value)">启用
                	<input type="radio" name="enableKey" value="N" checked="checked" onClick="onSelect(this.value)">不启用
                </td>
         </tr>
			<tr id="showTr" style="display: none">
            	<td align="right"> key ：<br></td>
                <td align="left"><input type="text" name="keyCode" value="0123456789ABCDE" style="width: 150px;">
                    <button class="smlbtn" name="key" type="button" onclick="addKey()">绑定key</button>
                <br></td>
              </tr>
			<%//} %>
			 -->
		  <tr height="35">
			<td align="right"> 用户备注 ：</td>
			<td align="left">
				<textarea class="from" name="obj.description" id="description" class="normal">${obj.description }</textarea>
			</td>
		   </tr>
       </table>
		<BR>
        </span>  
	</fieldset>
		<br>
	 <table border="0" cellspacing="0" cellpadding="0" width="100%">
		  <tr height="35">
			<td width="30%"><div align="center">
				<button  class="btn_sec" onClick="frmChk()" id="update">修改</button>
			</div></td>
			<td width="30%"><div align="center">
			<button class="btn_sec" onclick="window.close()">关闭</button>
			</div></td>
		  </tr>
	 </table>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function frmChk()
{
	var usernamelen = getLength(document.getElementById("name").value);
	var descriptionlen = getLength(document.getElementById("description").value);
	var mark = true;
	var sign = false;
	//if(frm.enableKey[0].checked && frm.keyCode.value == '')
	//{
	//	alert("请输入key值！");
	//	frm.keyCode.focus();
	//	return false;
	//}
	if(usernamelen>32 || descriptionlen>64){
		mark = false;
		alert("您提交的数据长度过大。\n请保证用户姓名长度不超过32，备注长度不超过64。");
	}
	
	if(mark){			
		if(userConfirm()){
			sign = true;
		  }else{
			return false;
		  }
	}
	//防止重复提交
	if(sign){
			frm.action = "<%=basePath%>/account/memberUser/update.action";
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
  var traderId=document.getElementById("userid").value;
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
      alert("USB身份验证盘驱动程序错误！");	
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
    alert("请安装安全控件！");	
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

</SCRIPT>
<%@ include file="/public/footInc.jsp"%>
