<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.Date'%>

<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %>
	<title>����</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
	function balance(){
			if(confirm("ȷ�Ͽ�ʼ���㣡"))
			{
				disableBtn();
				formNew.submit();
			}
		}
		function disableBtn(){
			formNew.submitBtn.disabled = true;
		}
	</script> 
</head>
<body>
<form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=balanceVoucher" method="POST">
		<fieldset width="100%">
		<legend>�������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                    <b><font size="3">��������ƾ֤�����ʼ�������</font></b><br><br>
                </td>
              </tr>
              <tr height="35">
			  	<td align="right"></td>
                <td align="left">
                	<b>ע��</b>ƾ֤�������Բ鿴�������ձ����Լ��������̵�ǰ�ʽ���ȷ�������Ƿ���ȷ��<br>
                </td>
              </tr>
              <tr height="40">
                <td colspan="4"></td>
              </tr>
              
              <tr height="30">
                <td colspan="4"><div align="center"><input type="hidden" name="sign">
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="balance();">ȷ��</button>&nbsp;
                </div></td>
              </tr>
              <tr height="100%">
              	<td></td>
              </tr>
          </table>
		</fieldset>
    </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>