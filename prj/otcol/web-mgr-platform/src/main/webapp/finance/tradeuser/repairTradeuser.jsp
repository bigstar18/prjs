<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.TradeuserManager' %>
<%@ page import='gnnt.MEBS.finance.unit.Tradeuser' %>
<html> 
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
	<title>�޸Ľ�����</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{ 
		    if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.passwords.value)
			{
			   alert("�����ȷ�����벻һ��");
			   return;
			}
			formNew.submit();
		}
	</script> 
</head>
<body>
      <form id="formNew" action="<%=basePath%>/tradeuserRepair.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>�����̻�����Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ��</td>
                <td align="left">
                	<input class="readonly" id="firmId" name="firmId" value="<c:out value='${param.firmId}'/>" style="width: 150px;" reqfv="required;�û�ID" readonly>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ���� ��</td>
                <td align="left">
                	<input class="normal" type="password" name="password"  style="width: 150px;" reqfv="required;����">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> <%=CONFORMPWD%> ��</td>
                <td align="left">
                	<input class="normal" type="password" name="passwords"  style="width: 150px;" reqfv="required;ȷ������">
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