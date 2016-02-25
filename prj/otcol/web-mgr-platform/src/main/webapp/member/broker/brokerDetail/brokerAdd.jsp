<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/headInc.jsp"%>

<html>
  <head>
	<title>添加系统用户</title>
	<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
	<script language="javascript" src="<%=request.getContextPath()%>/common/public/jslib/passwordStrength.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/delivery/public/jstools/jquery.js"></script>
  </head>
  <body>
    <form name=frm id=frm method="post" action="<%=brokerControllerPath %>brokerAdd">
		<fieldset width="100%">
		<legend>添加<%=BROKER%></legend>
		<BR>
		<span>
		<table border="0" cellspacing="0" cellpadding="0" width="50%" align="center">
		  
		  <tr height="35">
            	<td align="right"> <%=BROKERID%> ：</td>
                <td align="left">
                	<input name="brokerid" type="text" class="text" style="width: 180px;" onkeypress="onlyNumberAndCharInput()" maxlength="4"><b><font color=red>&nbsp;*</font><b/>
                </td>
          </tr>
          <tr height="35">
                <td align="right"> <%=BROKERFRIM%> ：</td>
                <td align="left">
                	<input id="dealerId" name="firmId" type="text" class="text" style="width: 180px;"  onblur="dealTest();" onkeypress="onlyNumberAndCharInput()" maxlength="16" ><b><font color=red>&nbsp;* <span id="hint"></span></font><b/>
                </td>
        </tr>
         
        <tr height="35">
                <td align="right"> 密码 ：</td>
                <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" onblur="passwordYin(this.value)" onKeyUp="passwordStrength(this.value)" onkeypress="notSpace()"  maxlength="64"><b><font color=red>&nbsp;*</font></b>
                  <div id="passwordPrompt">
                    <div style="width:70px; float:left;">密码强度：</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                  </div>
                  <div id="msg"></div>
                </td>
        </tr>
        <tr height="35">       
                <td align="right"> 确认密码 ：</td>
                <td align="left">
                	<input name="password1" type="password" class="text" style="width: 180px;" onkeypress="notSpace()" maxlength="16"><b><font color=red>&nbsp;*</font><b/>
                </td>
              </tr>
			  <tr height="35">
            	<td align="right"> 名称 ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 180px;"  maxlength="32"><b><font color=red>&nbsp;*</font><b/>
                </td>
			  </tr>
			  <tr height="35">
                <td align="right"> 市场开发人员：</td>
                <td align="left">
                	<input id="dealerId" name="marketManager" type="text" class="text" style="width: 180px;" maxlength="20" >
                </td>
       		 </tr>
			  <tr height="35">
            	<td align="right"> 电话 ：</td>
                <td align="left">
                	<input name="telephone" type="text" class="text" style="width: 180px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 手机 ：</td>
                <td align="left">
                	<input name="mobile" type="text" class="text" style="width: 180px;" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 电子邮件 ：</td>
                <td align="left">
                	<input name="email" type="text" class="text" style="width: 180px;" maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 所在省份 ：</td>
                <td align="left">
                	<input name="locationProvince" type="text" class="text" style="width: 180px;" maxlength="20">
                </td>
			  </tr>
			  <tr height="35">
            	<td align="right"> 地址 ：</td>
                <td align="left">
                	<input name="address" type="text" class="text" style="width: 180px;" maxlength="32">
                </td>
			  </tr>
              <tr height="35">
                <td align="right"> 备注 ：</td>
                <td align="left">
                	<textarea name="note" class="normal"></textarea>
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
				  <input type="button" onclick="return frmChk()" class="btn" value="保存">&nbsp;&nbsp;
  			    <input name="back" type="button" onclick="gotoUrl('<%=brokerControllerPath %>brokerList');" class="btn" value="返回">
				</div></td>
			  </tr>
		 </table>
</form>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function frmChk()
{
	if(Trim(frm.brokerid.value) == "")
	{
		alert("<%=BROKERID%>不能为空！");
		frm.brokerid.focus();
		return false;
	}
	else if(getLength(Trim(frm.brokerid.value))>4)
	{
		alert("加盟商账号长度不能大于4位！");
		frm.brokerid.focus();
		return false;
	}
	else if(Trim(frm.firmId.value) == "")
	{
		alert("<%=FIRMID%>不能为空！");
		frm.firmId.focus();
		return false;
	}
	else if(Trim(frm.password.value) == "")
	{
		alert("密码不能为空！");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(Trim(frm.password.value),8))
	{
		alert("密码长度不能少于8位，可包含字母、数字、特殊符号,请重新输入！");
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
		alert("两次输入的密码不一致！");
		frm.password1.focus();
		return false;
	}
	else if(Trim(frm.name.value) == "")
	{
		alert("名称不能为空! ");
		frm.name.focus();
		return false;
	}
	else if(getLength(Trim(frm.name.value))>30)
	{
		alert("名称长度不能超过30！");
		frm.name.focus();
		return false;
	}
	if((frm.email.value!="")&&!checkEmail(frm.email))
	{
	  return;
	}
	/*else if(parseFloat(frm.rewardRate.value)<0)
	{
		alert("手续费佣金比例不能小于0! ");
		frm.rewardRate.focus();
		return false;
	}
	else if(parseFloat(frm.rewardRate.value)>100)
	{
		alert("手续费佣金比例不能大于100%！");
		frm.rewardRate.focus();
		return false;
	}*/
	
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
    
function dealTest(){
	var dealerId = $("#dealerId").val();
	$.ajax({
			type:"post",
			url:"<%=brokerControllerPath%>getFirmInfo",
			data:"firmId=" + dealerId,
			success : function(data){
						if(data==1){
						  $("#dealerId").val("");
						  $("#hint").html("加盟商账号必须有对应的交易商账号");

						}else{
							$("#hint").html("");
						}
			}
		});
}

//-->
</SCRIPT>