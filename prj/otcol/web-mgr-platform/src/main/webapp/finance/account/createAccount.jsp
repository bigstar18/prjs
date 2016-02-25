<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>创建科目</title> 
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{
			if(!checkValue("formNew"))
				return;
			if(confirm("您确定提交吗？")) {
				formNew.submit();
			}
			
		}
	</script> 
</head>
<body>
        <form id="formNew" action="<%=basePath%>/accountController.spr?funcflg=accountAdd" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>科目基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=BITCODE%> ：</td>
                <td align="left">
                	<input class="normal" name="code" id="code" style="width: 150px;" reqfv="required;科目代码" onkeypress="onlyNumberInput()" maxlength="16">
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> <%=BITCODENAME%> ：</td>
                <td align="left">
                	<input name="name" type="text" class="text" style="width: 150px;" reqfv="required;科目名称" onkeypress="onlyNumberAndCharInput()" maxlength="32">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 借贷方向 ：</td>
                <td align="left">
                	<select class="normal" name="DCFlag" id="dcflag" style="width: 150px;">
                		<option value="D">借方</option>
                		<option value="C">贷方</option>
                	</select>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"> 科目级别 ：</td>
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
                  <button class="smlbtn" type="button" onclick="doSubmit();">提交</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">关闭窗口</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../public/footInc.jsp" %>