<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>
<base target="_self"> 
<html>
  <head>
	<title>修改系统用户</title>
</head>

	<script language="javascript">
	
		<c:if test='${not empty resultMsg}'>
				window.close();
				window.returnValue="123";
		</c:if>
	</script>

<body>
<form name=frm id=frm  method="post" action="<%=brokerControllerPath%>brokerMod">
		<fieldset width="100%">
		<legend>修改<%=BROKER%></legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
			  <tr height="35">
            	<td align="right"> <%=BROKERID%> ：</td>
                <td align="left">
                	${broker.brokerid}<input type=hidden name="brokerid" value="${broker.brokerid}">
                </td>
        </tr>     
        	  <tr height="35">
            	<td align="right"> <%=BROKERFRIM%> ：</td>
                <td align="left">
                	<input name="firmId" type="text" class="text" value="${broker.firmId}" style="width: 180px;" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>   
	   <tr height="35">
            	<td align="right"> 名称 ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" value="${broker.name}" style="width: 180px;" onkeypress="onlyNumberAndCharInput()" maxlength="16"><font color=red>&nbsp;*</font>
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 市场开发人员 ：</td>
                <td align="left">
                	<input name="marketManager" type="text" class="text" value="${broker.marketManager}" style="width: 180px;"  maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 电话 ：</td>
                <td align="left">
                	<input name="telephone" type="text" class="text" value="${broker.telephone}" style="width: 180px;" onkeypress="numberPass()" maxlength="16">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 手机 ：</td>
                <td align="left">
                	<input name="mobile" type="text" class="text" value="${broker.mobile}" style="width: 180px;" onkeypress="numberPass()" maxlength="16">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 电子邮件 ：</td>
                <td align="left">
                	<input name="email" type="text" class="text" value="${broker.email}" style="width: 180px;">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 所在省份 ：</td>
                <td align="left">
                	<input name="locationProvince" type="text" class="text" value="${broker.locationProvince}" style="width: 180px;">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 地址 ：</td>
                <td align="left">
                	<input name="address" type="text" class="text" value="${broker.address}" style="width: 180px;">
                </td>
			  </tr>
              <tr height="35">
                <td align="right"> 备注 ：</td>
                <td align="left">
                	<textarea name="note" class="normal">${broker.note}</textarea>
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
				  <input type="hidden" name="opt">
				  <input type="button" onclick="frmChk()" class="btn" value="保存">&nbsp;&nbsp;
				  <input name="back" type="button" onclick="window.close()" class="btn" value="关闭">&nbsp;&nbsp;
				</div></td>
			  </tr>
		 </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
<!--
function frmChk()
{
	var namelen = getLength(frm.name.value);
	if( namelen > 30 )
	{
		alert("用户名长度不能超过30！");
		frm.name.focus();
		return false;
	}
		else if(Trim(frm.name.value) == "")
	{
		alert("名称不能为空! ");
		frm.name.focus();
		return false;
	}
	if((frm.email.value!="")&&!checkEmail(frm.email))
	{
	  return;
	}
	else
	{
	   if(userConfirm()){
		frm.submit();
	   }else{
		return false;
	   }	
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
//控制创建交易用户复选框
function checkTradeUser(){
      if(document.frm.tradeUserCheck.checked){
        document.frm.tradeUser.value="true";	
      }else{
        //document.frm.tradeUser.value="0";	
      }
    }
//-->
</SCRIPT>