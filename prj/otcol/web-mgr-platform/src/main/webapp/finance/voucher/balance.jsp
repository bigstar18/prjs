<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.Date'%>

<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %>
	<title>结算</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
	function balance(){
			if(confirm("确认开始结算！"))
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
		<legend>财务结算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                    <b><font size="3">第三步：凭证入总帐及分类帐</font></b><br><br>
                </td>
              </tr>
              <tr height="35">
			  	<td align="right"></td>
                <td align="left">
                	<b>注：</b>凭证结算后可以查看【结算日报表】以及【交易商当前资金】以确定结算是否正确。<br>
                </td>
              </tr>
              <tr height="40">
                <td colspan="4"></td>
              </tr>
              
              <tr height="30">
                <td colspan="4"><div align="center"><input type="hidden" name="sign">
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="balance();">确认</button>&nbsp;
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