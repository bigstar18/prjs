<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>�����û�</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.confirmPwd.value){
				alert("������ȷ�Ͽ��һ�£����������룡");
				return;
			}
			formNew.submit();
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/userCreate.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>�û�������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=USERID%> ��</td>
                <td align="left">
                	<input class="normal" name="userId" id="userId" style="width: 150px;" reqfv="required;�û�ID">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=USERNAME%> ��</td>
                <td align="left">
                	<input name="userName" type="text" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> �û����� ��</td>
                <td align="left">
                	<input id="password" name="password" type="password" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CONFORMPWD%> ��</td>
                <td align="left">
                	<input id="confirmPwd" type="password" class="text" style="width: 150px;" reqfv="required;�û�����">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �û�״̬ ��</td>
                <td align="left">
                	<select class="normal" name="enabled" id="enabled" style="width: 150px;">
                		<option value="1">����</option>
                		<option value="0">����</option>
                	</select>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �û�Ȩ�� ��</td>
                <td align="left">
                	<input type="checkbox" name="role" value="roleAdmin">����Ա
                	<input type="checkbox" name="role" value="roleInput">¼��Ա
                	<input type="checkbox" name="role" value="roleConfirm">ȷ��Ա
                	<input type="checkbox" name="role" value="roleAudit">���Ա
                	<input type="checkbox" name="role" value="roleQuery">��ѯ�û�
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