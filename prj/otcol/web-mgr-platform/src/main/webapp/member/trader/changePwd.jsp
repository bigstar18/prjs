<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.unit.Trader' %>
<html> 
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>�޸Ľ���Ա����</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit() 
		{ 
			if(Trim(formNew.password.value) == "")
			{
				alert("���벻��Ϊ�գ�");
				formNew.password.focus();
				return false;
			}
			else if(!chkLen(Trim(formNew.password.value),6)){
				alert("���볤�Ȳ�������6λ,���������룡");
				formNew.password.focus();
				return false;
			}
			else if(formNew.password.value!=formNew.passwords.value)
			{
			   alert("�����ȷ�����벻һ��");
			   formNew.passwords.focus();
			   return false;
			}
			else{
			   formNew.submit();
			}
		}
	</script> 
</head>
<body>
      <form id="formNew" action="<%=basePath%>/firmController.mem?funcflg=changePwdTrader" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>����Ա����</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=TRADERID%> ��</td>
                <td align="left">
                	<input class="readonly" id="traderId" name="traderId" value="<c:out value='${param.traderId}'/>" style="width: 150px;" reqfv="required;����ԱID" readonly>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
                	<input class="normal" type="password" name="password"  style="width: 150px;" reqfv="required;����" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ȷ������ ��</td>
                <td align="left">
                	<input class="normal" type="password" name="passwords"  style="width: 150px;" reqfv="required;ȷ������" onkeypress="onlyNumberAndCharInput()" maxlength="16">
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