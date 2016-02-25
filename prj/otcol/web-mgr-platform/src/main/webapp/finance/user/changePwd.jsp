<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.UserManager' %>
<%@ page import='gnnt.MEBS.finance.unit.User' %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>���Ŀ���</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{ 
			if(!checkValue("formNew"))
				return;
			if(formNew.newPwd.value!=formNew.confirmPwd.value){
				alert("�¿�����ȷ�Ͽ��һ�£����������룡");
				return;
			}
			formNew.submit();
		}
	</script>  
</head>
<body>
        <form id="formNew" action="<%=basePath%>/userChangePwd.spr" method="POST" targetType="hidden">
		<fieldset width="100%">
		<legend>�û�������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=USERID%> ��</td>
                <td align="left">
                	<input class="readonly" id="userId" name="userId" value="<c:out value='${logonUser.userId}'/>" style="width: 150px;" reqfv="required;�û�ID" readonly>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=USERNAME%> ��</td>
                <td align="left">
                	<input name="userName" type="text" value="<c:out value='${logonUser.userName}'/>" class="readonly" style="width: 150px;" reqfv="required;�û�����" readonly>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=OLDPWD%> ��</td>
                <td align="left">
                	<input id="oldPwd" name="oldPwd" type="password" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=NEWPWD%> ��</td>
                <td align="left">
                	<input id="newPwd" name="newPwd" type="password" class="text" style="width: 150px;" reqfv="required;�¿���">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CONFORMPWD%> ��</td>
                <td align="left">
                	<input id="confirmPwd" type="password" class="text" style="width: 150px;" reqfv="required;ȷ�Ͽ���">
                </td>
              </tr>
              <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">�ύ</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>