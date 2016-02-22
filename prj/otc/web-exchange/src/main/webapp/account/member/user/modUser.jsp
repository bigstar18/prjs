<%@ page contentType="text/html;charset=GBK" %>
<jsp:directive.page import="gnnt.MEBS.common.model.User"/>
<%@ include file="/common/public/common.jsp"%>
<script language="javascript">
		var rightMap=${sessionScope.rightMap};
</script>
<script type="text/javascript" src="<%=basePath%>/public/limit.js"
			defer="defer"></script>
<head>
   <title>会员管理员信息</title>
</head>
<body class="st_body" style="overflow-y:hidden">
     <form name="frm" id="frm" method="post" targetType="hidden">
     <div class="div_scromin">
		<table border="0" width="90%" align="center">
				<tr>
					<td>
						<div class="st_title"><img src="<%=skinPath%>/cssimg/st_ico1.gif" align="absmiddle" />&nbsp;&nbsp;会员管理员信息</div>
						<table border="0" cellspacing="0" cellpadding="4" width="100%"
					align="center" class="st_bor">
					<tr height="35" width="38%">
						<td align="right" class="td_size" width="38%">
							用户代码:
						</td>
						<td class="td_size">
							${obj.userId }
               	<input type="hidden" class="from" id="userid" name="obj.userId" value="${obj.userId}">
               	<input type="hidden" class="from" name="obj.password" value="${obj.password}">
               	<input type="hidden" class="from" name="obj.skin" value="${obj.skin}">
						</td>
					</tr>
					<tr height="35">
						<td align="right" class="td_size">
							用户姓名:
						</td>
						<td class="td_size">
							${obj.name}<%--<input class="input_text" name="obj.name" id="name" type="text" class="text" value="" >
						--%></td>
					</tr>
					<tr height="35">
						<td align="right" class="td_size">
							用户描述:
						</td>
						<td class="td_size">
							${obj.description }<%--<textarea class="from" name="obj.description" id="description" class="normal"></textarea>
						--%></td>
					</tr>
				</table>
					</td>
				</tr>
			</table> 
			</div>
			<div class="tab_pad">
	 <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
		  <tr height="35">
			<td align="center">
				<button  class="btn_sec" onClick="frmChk()" id="update">保存</button>
			</td>
			<td align="center">
			<button class="btn_sec" onclick="window.close()">关闭</button>
			</td>
		  </tr>
	 </table>
	 </div>
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
