<%@ include file="../../../public/session.jsp"%>
<%@ page language="java" pageEncoding="GBK"%>
<base target="_self">
<html>
  <head>
    <title>�򷽽���ӯ����Ϣ</title>
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
		<br>	
		<fieldset>
			<legend class="common"><b>�򷽽���ӯ����Ϣ</b></legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">�򷽽����̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">��Ʒ���룺</td>
					<td align="left" width="50%">${settleMatch.commodityId }</td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">����������</td>
					<td align="left" width="50%">${settleMatch.weight }</td>
				</tr>
				<tr class="common">
					<td align="right">�򷽻�׼���</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�򷽼�����ˮ���</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref+settleMatch.HL_Amount }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�����򷽻��</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">�򷽽���ӯ����</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="SettlePL_B1();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="����" onclick="window.close();">
					</td>
				</tr>
				
				<tr class="common"><td align="center" width="100%" colspan="2">&nbsp;</td></tr>
				
				<tr class="common">
				  <td width="100%" colspan="2">
				    <font color="red">ע������ӯ������Զ�������ȡ�ġ�</font>
				  </td>
				</tr>
				
			</table>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function SettlePL_B1()
	{
		var SettlePL_B = frm.thisPayMent.value;
		if(SettlePL_B.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(SettlePL_B) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ���򷽽���ӯ��Ϊ��"+SettlePL_B+"��")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="SettlePL_B";
	     		submark = true;
	     	}
	     	if(submark){
				frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleSettlePL_B&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>