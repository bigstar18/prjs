<%@ page contentType="text/html;charset=GBK" %>

<html  xmlns:MEBS>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
	<title>创建交易商</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{ 
			if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.passwords.value)
			{
			   alert("密码和确认密码不一致");
			   return;
			}
			formNew.submit(); 
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/tradeuserCreate.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>交易商基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ：</td>
                <td align="left">
                	<input class="normal" name="firmId" id="firmId" type="text" style="width: 150px;" reqfv="required;用户ID">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 密码 ：</td>
                <td align="left">
                	<input class="normal" type="password" name="password"  id="firmId" style="width: 150px;" reqfv="required;密码">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> <%=CONFORMPWD%> ：</td>
                <td align="left">
                	<input class="normal" type="password" name="passwords" id="firmId" style="width: 150px;" reqfv="required;确认密码">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=FIRMNAME%> ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;用户名称">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=FULLNAME%> ：</td>
                <td align="left">
                	<input name="fullname" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 开户银行 ：</td>
                <td align="left">
                	<input name="bank" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 银行帐号 ：</td>
                <td align="left">
                	<input name="bankAccount" type="text" class="text" style="width: 150px;" >
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 地址 ：</td>
                <td align="left">
                	<input name="address" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 联系人 ：</td>
                <td align="left">
                	<input name="contactMan" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 联系人电话 ：</td>
                <td align="left">
                	<input name="phone" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 联系人传真 ：</td>
                <td align="left">
                	<input name="fax" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 邮编 ：</td>
                <td align="left">
                	<input name="postCode" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> eMail ：</td>
                <td align="left">
                	<input name="email" type="text" class="text" style="width: 150px;">
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>