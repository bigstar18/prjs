<%@ page contentType="text/html;charset=GBK" %>
<%@ page import='gnnt.MEBS.member.firm.unit.Trader' %>
<html> 
  <head>
    <%@ include file="../../public/headInc.jsp" %>
    <IMPORT namespace="MEBS" implementation="<%=basePathF%>/public/jstools/calendar.htc">
	<title>֧��Ӷ��</title>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script>
		function doSubmit() 
		{ 
		    if(!checkValue("formNew"))
				return;
			if(parseFloat(formNew.money.value) > parseFloat(formNew.amount.value))
			{
			   alert("֧��Ӷ���ܴ��ڴ���Ӷ��");
			   return;
			}
			formNew.submit();
		}	
	</script> 
</head>
<body>
      <form id="formNew" action="<%=brokerRewardControllerPath%>brokerRewardPayMoney" method="POST" targetType="hidden" callback="closeDialog(1);">
		<fieldset width="100%">
		<legend>֧��Ӷ��</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
                <td align="right"> �����̱�� ��</td>
                <td align="left">
                	<input class="readonly" id="brokerId" name="brokerId" value="<c:out value='${brokerReward.brokerId}'/>" style="width: 150px;" reqfv="required;�����̱��" readonly>
                	<input type = "hidden" id="occurDate" name="occurDate" value="<c:out value='${brokerReward.occurDate}'/>">
                	<input type = "hidden" id="moduleId" name="moduleId" value="<c:out value='${brokerReward.moduleId}'/>">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> ����Ӷ�� ��</td>
                <td align="left">
                	<input class="readonly" id="amount" name="amount" value="<c:out value='${brokerReward.amount}'/>" style="width: 150px;" reqfv="required;����Ӷ��">
                </td>
              </tr>
              <tr height="35">
                <td align="right"> �Ѹ�Ӷ�� ��</td>
                <td align="left">
                	<input class="readonly" id="paidAmount" name="paidAmount" value="<c:out value='${brokerReward.paidAmount}'/>" style="width: 150px;" reqfv="required;�Ѹ�Ӷ��">
                	
                </td>
              </tr>
               <tr height="35">
                <td align="right"> ֧��Ӷ�� ��</td>
                <td align="left">
                	<input class="normal" type="text" id="money" name="money"  style="width: 150px;" >
                </td>
              </tr>
               <tr height="35">
                <td colspan="2"><div align="center">
                  <button class="smlbtn" type="button" onclick="doSubmit();">�ύ</button>&nbsp;
      			  <button class="smlbtn" type="button" onclick="window.close()">�رմ���</button>
                </div></td>
              </tr>
          </table>
		</fieldset>
        </form>
</body>
</html>
<%@ include file="../../public/footInc.jsp" %>