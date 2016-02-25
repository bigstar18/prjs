<%@ page contentType="text/html;charset=GBK" %>

<html>
  <head>
    <%@ include file="public/headInc.jsp" %>
	<title>帐务系统说明</title>
</head>
<body>
     <fieldset width="80%">
		<legend>帐务系统说明</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35" colspan="2">&nbsp;</tr>
			  <tr height="35">
			  	<td width="20%"></td>
                <td align="left"><br><br>
                	<b>帐务系统实现以下功能：</b><br><br>
                	<li><b>录入手工凭证：支持一借多贷或多借一贷凭证，不能录入多借多贷凭证。</b></li><br><br>
                	<li><b>审核手工凭证：涉及到商户应付账款的凭证审核通过后将直接对交易系统发生作用。</b></li><br><br>
                	<li><b>抽取以下交易系统电脑凭证：保证金、交易费、货款、返还保证金</b></li><br><br>
                	<li><b>手工凭证及电脑凭证记入帐簿</b></li><br><br>
                	<li><b>进行每日结算：每个交易日所有科目都有一条结算数据。</b></li><br><br>
                	<li><b>进行每日客户总账表生成</b></li><br><br>
                	<li><b>确认结算结果，如果需要则重做结算。</b></li><br><br>
                </td>
              </tr>
              <tr height="100%">
              	<td></td>
              </tr>
          </table>
	</fieldset>
</body>
</html>