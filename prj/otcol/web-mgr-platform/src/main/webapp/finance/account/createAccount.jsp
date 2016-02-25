<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>������Ŀ</title> 
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(confirm("��ȷ���ύ��")) {
				formNew.submit();
			}
			
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/accountController.spr?funcflg=accountAdd" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>��Ŀ������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=BITCODE%> ��</td>
                <td align="left">
                	<input class="normal" name="code" id="code" style="width: 150px;" reqfv="required;��Ŀ����" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=BITCODENAME%> ��</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;��Ŀ����" onkeypress="onlyNumberAndCharInput()" maxlength="32">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ������� ��</td>
                <td align="left">
                	<select class="normal" name="DCFlag" id="dcflag" style="width: 150px;">
                		<option value="D">�跽</option>
                		<option value="C">����</option>
                	</select>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> ��Ŀ���� ��</td>
                <td align="left">
                	<select class="normal" name="accountLevel" id="accountLevel" style="width: 150px;">
                		<option value="1">1</option>
                		<option value="2">2</option>
                		<option value="3">3</option>
                	</select>
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