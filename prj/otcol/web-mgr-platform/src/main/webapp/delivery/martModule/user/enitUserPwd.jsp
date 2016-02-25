<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="../../public/session.jsp"%>
<html>
  <head>
    <base target="_self">  
    
    <title>修改超级管理员密码</title> 
    
	<link rel="stylesheet" type="text/css" href="<%=skinPath%>/passwordStrength.css"/>
	<script language="javascript" src="<%=request.getContextPath()%>/common/public/jslib/passwordStrength.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/common/jslib/tools.js"></script>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
		window.returnValue= true;
		window.close();
	</script>
</c:if>
	
  </head>
  
  <body>
    <form id="frm" name="frm" method="POST" action="<%=basePath%>servlet/userController.${POSTFIX}?funcflg=editUserPwd">
	<fieldset width="100%">
	<legend>修改超级管理员密码</legend>
	    <input type="hidden" id="userId" name="userId" value="${userId }" />
	    <input type="hidden" id="warehouseId" name="warehouseId" value="${warehouseId }" />
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="200">
			<tr>
			  <td align="right">管理员编号：</td>
                <td align="left">${userId }</td>
			</tr>
			<tr>
				<td align="right">密码：</td>
                <td align="left">
                  <input name="password" type="password" class="text" style="width: 180px;" maxlength="14"  onKeyUp="passwordStrength(this.value)" onblur="passwordYin(this.value)" onkeypress="notSpace()">&nbsp;<font color="red">*</font>
                  <div id="passwordPrompt" >
                    <div style="width:40px; float:left;">强度：</div>
                    <div id="passwordStrength" class="strength0"></div>
                    <div id="passwordDescription"></div>
                    <div style="clear: both;">密码长度8-14位，字母区分大小写</div>
                  </div>
                  <div id="msg"></div>
                </td>
            </tr>
            <tr>
				<td align="right">重复密码：</td>
                <td align="left">
                	<input name="password1" type="password" class="text" style="width: 180px;" maxlength="14" onkeypress="notSpace()" >&nbsp;<font color="red">*</font>
                </td>
              </tr>
              <tr>
                <td colspan="2"><div align="center">
	              <input type="button" value="确定" class="smlbtn" type="button" onclick="frmChk()">&nbsp;&nbsp;
	              <button class="smlbtn" type="button" onclick="window.close()">返回</button>
      			</td>
              </tr>
          </table>
	  </fieldset>
    </form>
  </body>
  
</html>
<script type="text/javascript">
  function frmChk(){
    if(frm.name.value == "")
	{
		alert("名称不能为空！");
		frm.name.focus();
		return false;
	}
	else if(frm.password.value == "")
	{
		alert("密码不能为空！");
		frm.password.focus();
		return false;
	}
	else if(!chkLen(frm.password.value,8))
	{
		alert("密码长度不能少于8位，可包含字母、数字、特殊符号,请重新输入！");
		frm.password.focus();
		return false;
	}
	else if(frm.password1.value == "")
	{
		alert("重复密码不能为空！");
		frm.password1.focus();
		return false;
	}
	else if(frm.password1.value != frm.password.value)
	{
		alert("两次输入的密码不一致！");
		frm.password1.focus();
		return false;
	}
	else{
	    frm.submit();
	}
  }
  </script>