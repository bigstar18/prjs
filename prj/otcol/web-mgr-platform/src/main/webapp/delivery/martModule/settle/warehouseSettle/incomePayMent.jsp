<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>���򷽻�����Ϣ</title>
<c:if test="${ not empty resultMsg }">
	<script type="text/javascript">
		window.returnValue="-1";
		window.close();
	</script>
</c:if>
  </head>
  <body>
	<form name="frm" method="post">
		<input type="hidden" name="matchId" value="${settleMatch.matchId }">
		<input type="hidden" name="moduleId" value="${settleMatch.moduleId }">
		<fieldset>
			<br>
			<legend class="common"><b>���򷽻�����Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%">
				<tr class="common">
					<td align="right" width="50%">�򷽽����̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">��ƷƷ�ִ��룺</td>
					<td align="left" width="50%">${settleMatch.breedId }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����������</td>
					<td align="left" width="50%">${settleMatch.weight }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�򷽽��ռۣ�</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.buyPrice }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�򷽻�׼���</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.buyPayout_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%"><b>�򷽼�����ˮ����:</b></td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�����򷽻��</td>
					<td align="left" width="50%"><fmt:formatNumber value="${settleMatch.buyPayout }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">������ˮ�����ٷֱȣ�</td>
					<td align="left" width="50%">
						<input type="text" name="percent" id="percent" style="width: 100px" onblur="checkMoneyForSettle('percent','�����տ���')">
						<font color="red">%</font>
						<input type="hidden" name="totalMoney" value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount }">
					</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">�����ջ��</td>
					<td align="left" width="50%">
						<input type="text" id="thisPayMent" name="thisPayMent" style="width: 100px" onblur="checkMoneyForSettle('thisPayMent')">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">&nbsp;</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="pay()">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
			</table>
				<br>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
</html>
<script type="text/javascript">
function pay()
{ 
  if(frm.percent.value=="" && frm.thisPayMent.value=="" || frm.thisPayMent.value ==""){
       alert("����������һ����");
  }
  else { 
     thisPay('ȷ�������տ���Ϊ��','<%=basePath%>servlet/settleMatchController.${POSTFIX}?funcflg=settleIncomePayMent&dd=');
   }
}
</script>