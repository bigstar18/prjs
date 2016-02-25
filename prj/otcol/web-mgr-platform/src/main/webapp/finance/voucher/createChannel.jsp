<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.List' %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>创建模板</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script> 
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			formNew.submit();
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=channelAdd" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>模板基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=CODEID%> ：</td>
                <td align="left">
                	<input class="normal" name="code" id="code" style="width: 80px;" reqfv="required;模板代码" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
                <td align="right">&nbsp;</td>
                <td align="left">&nbsp;
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=CODENAME%> ：</td>
                <td align="left" colspan="3">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;模板名称" onkeypress="onlyNumberAndCharInput()" maxlength="16">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=SUMMARYNO%> ：</td>
                <td align="left" colspan="3">
                	<input name="summaryNo" type="text" class="text" style="width: 80px;" reqfv="required;摘要号" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 借方<%=BITCODE%> ：</td>
                <td align="left">
                	<input name="debitCode" type="text" class="text" style="width: 80px;" reqfv="required;借方科目代码" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
                <td align="right">贷方<%=BITCODE%> ：</td>
                <td align="left">
                	<input name="creditCode" type="text" class="text" style="width: 80px;" reqfv="required;贷方科目代码" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 需要合同号 ：</td>
                <td align="left">
                	<select name="needContractNo"  class="normal" style="width: 80px">
					<option value="N">否</option>
          			<option value="Y">是</option>
				    </select>
                </td>
               <td align="right"> 备注 ：</td>
                <td align="left">
                	<textarea name="note" cols="20" rows="4"></textarea>
                </td>
              </tr>
          </table>
		</fieldset>
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr height="35">
         <td colspan="4"><div align="center">
           <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
		  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
         </div></td>
         </tr>
		</table>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>