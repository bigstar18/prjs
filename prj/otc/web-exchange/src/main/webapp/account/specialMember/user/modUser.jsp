<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<body style="overflow-y:hidden">
 <head>
		<title>特别会员管理员信息</title>
	</head>
 <OBJECT classid=clsid:0023145A-18C6-40C7-9C99-1DB6C3288C3A id="ePass" 
         STYLE="LEFT: 0px; TOP: 0px" width=0 height=0
         CODEBASE="GnntKey.cab#Version=1,0,0,5">
        </OBJECT>
     <form name="frm" id="frm" method="post" targetType="hidden">
		<div class="div_scromin">
			<table border="0" width="80%" align="center">
				<tr>
					<td>
					<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp; 特别会员管理员信息</div>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor">
		  <tr height="35">
           	<td align="right" class="td_size" width="40%"> 用户代码 ：</td>
               <td align="left" class="td_size">
               	${obj.userId }
               	<input type="hidden" id="userid" name="obj.userId" value="${obj.userId}">
               	<input type="hidden" id="userName" name="obj.name" value="${obj.name}">
               	<input type="hidden" name="obj.password" value="${obj.password}">
               	<input type="hidden" name="obj.skin" value="${obj.skin}">
               </td>
         </tr>
         <tr height="35">
			<td align="right" class="td_size"> 用户姓名 ：</td>
			<td align="left" class="td_size">
					${obj.name }
			</td>
		  </tr>
		  <tr height="35">
			<td align="right" class="td_size"> 用户备注 ：</td>
			<td align="left" class="td_size">
				${obj.description }<%--<textarea name="obj.description" id="description" class="normal"></textarea>
			--%></td>
		   </tr>
       </table>
       </td>
       </tr>
       </table>
       </div>
       <div class="tab_pad">
	 <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr>
		<!--<td align="center">
			<button  class="btn_sec" onClick="frmChk()" id="update">保存</button>
		</td> -->
		<td align="center">
			<button  class="btn_sec" onClick="window.close()">关闭</button>
		</td>
	</tr>
	 </table>
	 </div>
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
function frmChk()
{
	var mark = true;
	var sign = false;	
	if(mark){			
		if(userConfirm()){
			sign = true;
		  }else{
			return false;
		  }
	}
	//防止重复提交
	if(sign){
			frm.action = "<%=basePath%>/account/specialMemberUser/update.action";
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
