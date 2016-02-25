<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.finance.manager.TradeuserManager' %>
<%@ page import='gnnt.MEBS.finance.unit.Tradeuser' %>
<html> 
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePath%>/public/jstools/calendar.htc">
	<title>修改交易商</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit()
		{ 
		    if(!checkValue("formNew"))
				return;
			if(formNew.password.value!=formNew.passwords.value)
			{
			   alert("密码和确认密码不一致");
			   return;
			}
			formNew.submit();
		}
	</script> 
</head>
<body>
      <form id="formNew" action="<%=basePath%>/tradeuserRepair.spr" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>交易商基本信息</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> <%=FIRMID%> ：</td>
                <td align="left">
                	<input class="readonly" id="firmId" name="firmId" value="<c:out value='${param.firmId}'/>" style="width: 150px;" reqfv="required;用户ID" readonly>
                </td>
              </tr>
              <tr height="35">
                <td align="right"> 密码 ：</td>
                <td align="left">
                	<input class="normal" type="password" name="password"  style="width: 150px;" reqfv="required;密码">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> <%=CONFORMPWD%> ：</td>
                <td align="left">
                	<input class="normal" type="password" name="passwords"  style="width: 150px;" reqfv="required;确认密码">
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