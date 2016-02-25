<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>创建用户</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.confirmPwd.value){
				alert("口令与确认口令不一致，请重新输入！");
				return;
			}
			formNew.submit();
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/userCreate.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>用户基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=USERID%> ：</td>
                <td align="left">
                	<input class="normal" name="userId" id="userId" style="width: 150px;" reqfv="required;用户ID">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=USERNAME%> ：</td>
                <td align="left">
                	<input name="userName" type="text" class="text" style="width: 150px;" reqfv="required;用户名称">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 用户口令 ：</td>
                <td align="left">
                	<input id="password" name="password" type="password" class="text" style="width: 150px;" reqfv="required;用户口令">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CONFORMPWD%> ：</td>
                <td align="left">
                	<input id="confirmPwd" type="password" class="text" style="width: 150px;" reqfv="required;用户口令">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 用户状态 ：</td>
                <td align="left">
                	<select class="normal" name="enabled" id="enabled" style="width: 150px;">
                		<option value="1">可用</option>
                		<option value="0">禁用</option>
                	</select>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 用户权限 ：</td>
                <td align="left">
                	<input type="checkbox" name="role" value="roleAdmin">管理员
                	<input type="checkbox" name="role" value="roleInput">录入员
                	<input type="checkbox" name="role" value="roleConfirm">确认员
                	<input type="checkbox" name="role" value="roleAudit">审核员
                	<input type="checkbox" name="role" value="roleQuery">查询用户
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