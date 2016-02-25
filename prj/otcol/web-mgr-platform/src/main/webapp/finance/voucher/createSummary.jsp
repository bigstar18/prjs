<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>����ժҪ</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script> 
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(formNew.summaryNo.value.length!=3)
			{
			  alert("ժҪ�ű�������λ!");
			  return false; 
			}
			if(confirm("��ȷ���ύ��")) {
			  formNew.submit();
			}
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=summaryAdd" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>ժҪ������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=SUMMARYNO%> ��</td>
                <td align="left">
                	<input class="normal" name="summaryNo" id="summaryNo" style="width: 100px;" reqfv="required;ժҪ���" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                <td align="right"> <!-- ƾ֤���� �� -->&nbsp;</td>
                <td align="left">&nbsp;
                	<!-- <select id="voucherType" name="voucherType" class="normal" style="width: 100px">
						<option value="C">��</option>
          				<option value="P">��</option>
          				<option value="T">ת</option>
					</select> -->
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=DUMMARYNAME%> ��</td>
                <td align="left" colspan="3">
                	<input name="summary" type="text" class="text" style="width: 310px;" reqfv="required;ժҪ����">
                </td>
              </tr>
              </table>
		      </fieldset>
		      <fieldset width="100%">
		      <legend>�����ʽ���Ϣ</legend>
			  <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr height="35">
                <td align="right"> ����������Ŀ ��</td>
                <td align="left">
                	<select name="ledgerItem"  class="normal" style="width: 100px">
					<OPTION value=""></OPTION>
					<c:forEach items="${fieldList}" var="result">
			        <option value="${result.code}">${result.name}</option>
			        </c:forEach>
				    </select>
                </td>
                <td align="right"> �������ʽ������� ��</td>
                <td align="left">
                	<select id="fundDCFlag" name="fundDCFlag" class="normal" style="width: 100px">
						<option value="N">���漰</option>
          				<option value="C">�Ǵ���</option>
          				<option value="D">�ǽ跽</option>
					</select>
                </td>
              </tr>
              <tr height="35">
              	<!-- ����˶Է���Ŀ�����ѡ����д 2011-3-10 by feijl -->
                <td align="right"> �Է���Ŀ���� ��</td>
                <td align="left">
                	<input class="normal" name="accountCodeOpp" id="accountCodeOpp" style="width: 100px;" reqfv="required;�Է���Ŀ����" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
                <td align="right"> ������ ��</td>
                <td align="left">
                	<select name="appendAccount"  class="normal" style="width: 100px" >
					<option value="N">�޸���</option>
          			<option value="T">��ֵ˰</option>
          			<option value="W">������</option>
				    </select>
                </td>
              </tr>
          </table>
		</fieldset>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
         <td colspan="4"><div align="center">
           <button class="smlbtn" type="button" onclick="doSubmit();">�ύ</button>&nbsp;
		  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
         </div></td>
         </tr>
		</table>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>