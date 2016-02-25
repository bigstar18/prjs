<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
	<title>重做结算</title>
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
			if(confirm("确认要重做【"+formNew.redoBeginDate.value+"】以后的结算?")){
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
		<legend>重做结算说明</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                	<b>指定开始日期进行重做以下操作：</b><br><br>
                	<li><b>重做抽取交易系统电脑凭证</b></li><br><br>
                	<li><b>重做手工凭证及电脑凭证记入帐簿</b></li><br><br>
                	<li><b>重做每日结算</b></li><br><br>
                	<li><b>重做每日客户总账</b></li><br><br>
                </td>
              </tr>
              <tr height="35">
            	<td align="right"></td>
                <td align="left">重做开始日期 ：
                	<input name="redoBeginDate" type="text" class="text" style="width: 100px;" reqfv="required;开始日期">&nbsp;<font color="red">注意:日期的错误会造成数据丢失!</font>
                </td>
              </tr>
              <tr height="50">
                <td colspan="4"><div align="center">
                  <button id="submitBtn" class="mdlbtn" type="button" onclick="doSubmit();">重做结算</button>&nbsp;
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