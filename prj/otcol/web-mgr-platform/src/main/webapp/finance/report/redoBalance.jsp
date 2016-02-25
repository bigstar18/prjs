<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>��������</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function init(){
			if(formNew.redoBeginDate.value == null || formNew.redoBeginDate.value == '')
				formNew.redoBeginDate.value = '<%=nowDate%>';
		}
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(confirm("ȷ��Ҫ������"+formNew.redoBeginDate.value+"���Ժ�Ľ���?")){
				disableBtn();
				formNew.submit();
			}
		}
		function disableBtn(){
			formNew.submitBtn.disabled = true;
		}
	</script> 
</head>
<body onload="init();">
     <form id="formNew" action="<%=basePath%>/reportRedoBalance.spr" method="POST" targetType="hidden">
		<fieldset width="100%">
		<legend>��������˵��</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                	<b>ָ����ʼ���ڽ����������²�����</b><br><br>
                	<li><b>������ȡ����ϵͳ����ƾ֤</b></li><br><br>
                	<li><b>�����ֹ�ƾ֤������ƾ֤�����ʲ�</b></li><br><br>
                	<li><b>����ÿ�ս���</b></li><br><br>
                	<li><b>����ÿ�տͻ�����</b></li><br><br>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"></td>
                <td align="left">������ʼ���� ��
                	<input name="redoBeginDate" type="text" class="text" style="width: 100px;" reqfv="required;��ʼ����">&nbsp;<font color="red">ע��:���ڵĴ����������ݶ�ʧ!</font>
                </td>
              </tr>
              <tr height="50">
                <td colspan="4"><div align="center">
                  <button id="submitBtn" class="mdlbtn" type="button" onclick="doSubmit();">��������</button>&nbsp;
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