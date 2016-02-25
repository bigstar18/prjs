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
	<title>�������</title>
	<script language="javascript" src="<%=basePath%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePath%>/public/jstools/tools.js"></script>
	<script>
		function accountVoucher(){
			if(confirm("ȷ��ƾ֤���˼����㣿�˲���Ӧ�ڱ��ս�������Լ�û����ƾ֤�������ִ�У�"))
			{
				disableBtn();
				formNew.submit();
			}
		}
		function accountVoucher1(){
			if(confirm("ȷ�Ͽ�ʼ������㣿"))
			{
				disableBtn();
				formNew.submit();
			}
		}
		
		function accountVoucher2(){
			if(confirm("ȷ�Ͻ�����һ����"))
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
		<legend>�������</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%" height="350">
			  <tr height="35">
			  	<td width="30%"></td>
                <td align="left"><br><br>
                	<b><font size="3">��һ�������ݽ�����ˮ���ɵ���ƾ֤</font></b><br><br>
                	<font color="red">&nbsp;&nbsp;&nbsp;&nbsp;ע���˲���Ӧ�ڽ���ϵͳ��������</font><br><br>
                </td>
              </tr>
              <tr height="40">
                <td colspan="4"></td>
              </tr>
              <tr height="40">
                <td colspan="4"><div align="center">
                  <b>����������ڣ�<fmt:formatDate value='${maxDate}' pattern="yyyy-MM-dd"/></b>
                </div></td>
              </tr>
              <tr height="30">
                <td colspan="4"><div align="center">
                  <!-- <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher();">ƾ֤���˽���</button>&nbsp; -->
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher1();">���ɵ���ƾ֤</button>&nbsp;
                  <button id="submitBtn" class="lgrbtn" type="button" onclick="accountVoucher2();" <c:if test="${empty sign}">disabled</c:if>>��һ��</button>&nbsp;
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