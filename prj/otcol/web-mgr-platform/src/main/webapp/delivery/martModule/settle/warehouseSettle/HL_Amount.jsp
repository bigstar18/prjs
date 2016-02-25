<%@ page language="java" pageEncoding="GBK"%>
<%@ include file="../../../public/session.jsp"%>
<base target="_self">
<html>
  <head>
    <title>����ˮ��Ϣ</title>
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
		<fieldset>
			<legend class="common">
				<b>����ˮ��Ϣ</b>
				</legend>
			<span>
			<table class="common" align="center" width="100%" height="100%" border="0">
				<tr class="common">
					<td align="right" width="50%">�򷽽����̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_B }</td>
				</tr>
				<tr class="common">
					<td align="right" width="50%">���������̴��룺</td>
					<td align="left" width="50%">${settleMatch.firmID_S }</td>
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
					<td align="right">�򷽻�׼���</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�х��򷽻��</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.buyPayout }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">������׼���</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.sellIncome_Ref }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right">�Ѹ��������</td>
					<td align="left"><fmt:formatNumber value="${settleMatch.sellIncome }" pattern="#,##0.00"/></td>
				</tr>
				<tr class="common">
					<td align="right"><b>��ǰ����ˮ��</b></td>
					<td align="left"><b><fmt:formatNumber value="${settleMatch.HL_Amount }" pattern="#,##0.00"/></b></td>
				</tr>
				<tr class="common">	
					<td align="right" width="50%">������������ˮ��</td>
					<td align="left" width="50%">
						<input type="text" name="thisPayMent" style="width: 100px">
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="common">
					<td align="center" width="100%" colspan="2">
						<input type="button" name="subbtn" class="button" value="�ύ" onclick="setHL_Amount();">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="button" value="�ر�" onclick="window.close();">
					</td>
				</tr>		
			</table>
			</span>
		</fieldset>
		<input type="hidden" name="opt">
	</form>
</body>
<script type="text/javascript">
	function setHL_Amount()
	{
		var HL_Amount = frm.thisPayMent.value;
		if(HL_Amount.search("^-?\\d+(\\.\\d+)?$")!=0 || parseFloat(HL_Amount) == parseFloat(0)){
	        alert("������һ����0����!");
	        frm.thisPayMent.value="";
	        frm.thisPayMent.focus();
	        return false;
	     }else{
	     	var submark = false;
	     	if(confirm("ȷ��������ˮ����Ϊ��"+HL_Amount+"��")){
	     		frm.subbtn.disabled = true;
	     		frm.opt.value="HL_Amount";
	     		submark = true;
	     	}
	     	if(submark){
				frm.action="<%=basePath %>servlet/settleMatchController.${POSTFIX}?funcflg=settleHL_Amount&dd="+Date();
	     		frm.submit();
	     		}
	     }
	}
</script>
</html>