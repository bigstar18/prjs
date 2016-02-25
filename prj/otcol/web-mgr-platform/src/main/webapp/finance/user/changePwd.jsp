<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.UserManager' %>
<%@ page import='gnnt.MEBS.finance.unit.User' %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>更改口令</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{ 
			if(!checkValue("formNew"))
				return;
			if(formNew.newPwd.value!=formNew.confirmPwd.value){
				alert("新口令与确认口令不一致，请重新输入！");
				return;
			}
			formNew.submit();
		}
	</script>  
</head>
<body>
        <form id="formNew" action="<%=basePath%>/userChangePwd.spr" method="POST" targetType="hidden">
		<fieldset width="100%">
		<legend>用户基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=USERID%> ：</td>
                <td align="left">
                	<input class="readonly" id="userId" name="userId" value="<c:out value='${logonUser.userId}'/>" style="width: 150px;" reqfv="required;用户ID" readonly>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=USERNAME%> ：</td>
                <td align="left">
                	<input name="userName" type="text" value="<c:out value='${logonUser.userName}'/>" class="readonly" style="width: 150px;" reqfv="required;用户名称" readonly>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=OLDPWD%> ：</td>
                <td align="left">
                	<input id="oldPwd" name="oldPwd" type="password" class="text" style="width: 150px;" reqfv="required;用户口令">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=NEWPWD%> ：</td>
                <td align="left">
                	<input id="newPwd" name="newPwd" type="password" class="text" style="width: 150px;" reqfv="required;新口令">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CONFORMPWD%> ：</td>
                <td align="left">
                	<input id="confirmPwd" type="password" class="text" style="width: 150px;" reqfv="required;确认口令">
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