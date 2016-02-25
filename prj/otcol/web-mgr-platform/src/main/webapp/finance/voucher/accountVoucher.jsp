<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='java.util.Date'%>
<%@ page import='gnnt.MEBS.finance.util.SysData' %>
<%@ page import='gnnt.MEBS.finance.service.VoucherService' %>

<%
    VoucherService vs=(VoucherService)SysData.getBean("f_voucherService");
    Date maxDate = vs.getMaxBalanceDate();
	pageContext.setAttribute("maxDate",maxDate);
	String sign=(String)request.getParameter("sign");
	pageContext.setAttribute("sign",sign);
%>
<html>
  <head> 
    <%@ include file="../public/headInc.jsp" %> 
	<title>财务结算</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function accountVoucher(){
			if(confirm("确认凭证入账及结算？此操作应在本日交易完成以及没有新凭证的情况下执行！"))
			{
				disableBtn();
				formNew.submit();
			}
		}
		function accountVoucher1(){
			if(confirm("确认开始财务结算？"))
			{
				disableBtn();
				formNew.submit();
			}
		}
		
		function accountVoucher2(){
			if(confirm("确认进行下一步？"))
			{
				window.location="<%=basePath%>/voucherController.spr?funcflg=voucherCheckDate";
			}
		}
		function disableBtn(){
			formNew.submitBtn.disabled = true;
		}
	</script> 
</head>
<body>
     <form id="formNew" action="<%=basePath%>/voucherController.spr?funcflg=fundFlowIntoVoucher" method="POST">
		<fieldset width="100%">
		<legend>财务结算</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                	<b><font size="3">第一步：根据交易流水生成电脑凭证</font></b><br><br>
                	<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;注：此操作应在交易系统结算后进行</font><br><br>
                </td>
              </tr>
              <tr height="40">
                <td colspan="4"></td>
              </tr>
              <tr height="40">
                <td colspan="4"><div align="center">
                  <b>最近结算日期：<fmt:formatDate value='${maxDate}' pattern="yyyy-MM-dd"/></b>
                </div></td>
              </tr>
              <tr height="30">
                <td colspan="4"><div align="center">
                  <!-- <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher();">凭证入账结算</button>&nbsp; -->
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher1();">生成电脑凭证</button>&nbsp;
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher2();" <c:if test="${empty sign}">disabled</c:if>>下一步</button>&nbsp;
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