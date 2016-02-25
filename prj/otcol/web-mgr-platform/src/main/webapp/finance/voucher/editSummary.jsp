<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %>
	<title>�༭ժҪ</title>
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
        <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=summaryMod" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>ժҪ������Ϣ</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=SUMMARYNO%> ��</td>
                <td align="left">
                	<input class="normal" name="summaryNo" value="<c:out value='${summary.summaryNo}'/>" style="width: 100px;" readonly>
                </td>
                <td align="right"> <!-- ƾ֤���� �� -->&nbsp;</td>
                <td align="left">&nbsp;
                	<!-- <select id="voucherType" name="voucherType" class="normal" style="width: 100px">
						<option value="C">��</option>
          				<option value="P">��</option>
          				<option value="T">ת</option>
					</select>
                	 -->
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> ժҪ���� ��</td>
                <td align="left" colspan="3">
                	<input name="summary" value="<c:out value='${summary.summary}'/>" type="text" class="text" style="width: 310px;">
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
				    <script>
						formNew.ledgerItem.value = "<c:out value='${summary.ledgerItem}'/>"
					</script>
                </td>
                <td align="right"> �������ʽ������� ��</td>
                <td align="left">
                	<select id="fundDCFlag" name="fundDCFlag" class="normal" style="width: 100px">
						<option value="N">���漰</option>
          				<option value="C">�Ǵ���</option>
          				<option value="D">�ǽ跽</option>
					</select>
					<script>
						formNew.fundDCFlag.value = "<c:out value='${summary.fundDCFlag}'/>"
					</script>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ������ ��</td>
                <td align="left">
                	<select name="appendAccount"  class="normal" style="width: 100px">
					<option value="N">�޸���</option>
          			<option value="T">��ֵ˰</option>
          			<option value="W">������</option>
				    </select>
				    <script>
						formNew.appendAccount.value = "<c:out value='${summary.appendAccount}'/>"
					</script>
                </td>
                <td align="right"> &nbsp;</td>
                <td align="left">&nbsp;</td>
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